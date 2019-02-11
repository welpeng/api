package com.eleme.service.impl;

import com.eleme.autoconfig.NodejsProperties;
import com.eleme.dto.nodejs.RedPacketDTO;
import com.eleme.entity.Alt;
import com.eleme.response.nodejs.HongbaoResponse;
import com.eleme.service.NodejsService;
import com.eleme.utils.Connections;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NodejsServiceImpl implements NodejsService {

    @Autowired
    private NodejsProperties nodejsProperties;

    @Override
    public HongbaoResponse<RedPacketDTO> getHongbao(String url, String phone, List<Alt> cookies) throws IOException {
        String spec = nodejsProperties.getUrl() + nodejsProperties.getGetHongbao();
        Map<String, Object> arg = new HashMap<>();
        arg.put("url", url);
        arg.put("mobile", phone);
        arg.put("cookies", cookies);
        HongbaoResponse<RedPacketDTO> post = Connections.post(spec, arg, new TypeReference<HongbaoResponse<RedPacketDTO>>() {
        });
        return post;

    }




}
