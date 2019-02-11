package com.eleme.service.impl;

import com.eleme.constant.Constants;
import com.eleme.constant.ErrorCode;
import com.eleme.constant.ReceivingStatus;
import com.eleme.dao.AltDao;
import com.eleme.dao.ReceivingDao;
import com.eleme.dto.nodejs.RedPacketDTO;
import com.eleme.entity.Alt;
import com.eleme.entity.Receiving;
import com.eleme.exception.BusinessException;
import com.eleme.response.ReceivingResponse;
import com.eleme.response.nodejs.HongbaoResponse;
import com.eleme.service.NodejsService;
import com.eleme.service.ReceivingService;
import com.eleme.utils.Synchronizes;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

@Slf4j
@Service
public class ReceivingServiceImpl implements ReceivingService {
    @Autowired
    private ReceivingDao receivingDao;
    @Resource
    private AltDao altDao;
    @Autowired
    private NodejsService nodejsService;

    @Override
    public String getHongbao(String phoneNum, String url) {
        String urlKey = null;
        url = url.trim();
        url = url.replace("&amp;", "&");
        if (url.endsWith("]")) {
            url = url.substring(0, url.length() - 1);
        }
        URL spec = null;
        try {
           spec = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        urlKey = getParmeter(spec.getRef(), "sn");

        String receivingLock = Synchronizes.buildReceivingLock(urlKey);
        String userReceiveLock = Synchronizes.buildUserReceiveLock(phoneNum);


        String res = null;
        synchronized (receivingLock) {
            synchronized (userReceiveLock) {
                res = save(urlKey, url, phoneNum);
            }
        }
        return res;
    }

    @Override
    public String dispatch(Receiving receiving) {
        // 先将领取状态设置为领取失败
        receiving.setStatus(ReceivingStatus.FAILURE);
        List<Alt> cookies = altDao.getNumCookie(Constants.maxNumberCookie);
        Integer i = altDao.updateBatchUseNum(cookies);
        String res = receive(receiving, cookies);
        return res;
    }

    @Override
    public String receive(Receiving receiving, List<Alt> cookies) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        // 设置领取完成时间
        receiving.setGmtModified(timestamp);
        String url = receiving.getUrl();
        String phone = receiving.getUserId();
        HongbaoResponse<RedPacketDTO> resultResponse = null;
        try {
            resultResponse = nodejsService.getHongbao(url, phone, cookies);
        } catch (IOException e) {
            // TODO IOException 先记录错误日志，cookie 待处理
            log.error("receiving={}, cookies={}, available={}", receiving, cookies, e);
            receiving.setMessage(e.getClass().getSimpleName());
            receivingDao.save(receiving);
            return "领取发生错误";
        }

        int code = resultResponse.getCode();
        receiving.setMessage(resultResponse.getMessage());

        if (code < 0) {
            // TODO Node.js 服务抛出运行时异常先记录错误日志，cookie 待处理
            log.error("receiving={}, cookies={}, code={}", receiving, cookies, code);
            receivingDao.save(receiving);
            return null;
        }

        if (code == 222) {
                // 领取成功
                receiving.setStatus(ReceivingStatus.SUCCESS);

        }
        receivingDao.save(receiving);


        return resultResponse.getMessage();
    }



    private String save(String urlKey, String url, String phoneNum) {
        Receiving receiving = null;
        receiving = receivingDao.findByUrlKeyAndStatusNot(urlKey,
                ReceivingStatus.FAILURE);
        if (receiving != null) {
            throw new BusinessException(ErrorCode.RED_PACKET_EXIST,
                    "urlKey={}, application={}, status={}, receiving={}", urlKey,  ReceivingStatus.FAILURE,
                    receiving);
        }
        receiving = receivingDao.findByStatusAndUserId( ReceivingStatus.ING, phoneNum);
        if (receiving != null) {
            throw new BusinessException(ErrorCode.RECEIVE_WAIT, "application={}, status={}, userId={}, receiving={}",
                    ReceivingStatus.ING, phoneNum, receiving);
        }

        receiving = new Receiving();
        receiving.setGmtCreate(Timestamp.from(Instant.now()));
        receiving.setUrlKey(urlKey);
        receiving.setUrl(url);
        receiving.setStatus(ReceivingStatus.ING);
        receiving.setUserId(phoneNum);
        receivingDao.save(receiving);

        ReceivingResponse receivingResponse = new ReceivingResponse();
        BeanUtils.copyProperties(receiving, receivingResponse);

        String res = dispatch(receiving);

        return res;

    }

    private String getParmeter(String query, String name) {
        if (StringUtils.isEmpty(query) || StringUtils.isEmpty(name)) {
            return null;
        }
        Optional<String> optional = Stream.of(query.split("&")).filter(keyValue -> keyValue.startsWith(name + "="))
                .map(keyValue -> keyValue.split("=")[1]).findFirst();
        return optional.orElse(null);
    }

}
