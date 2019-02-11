package com.eleme.web;

import com.eleme.service.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.eleme.service.HongbaoService;
import java.io.IOException;

@Controller  
public class HongbaoController {  
	
	@Autowired
	private HongbaoService hongbaoService;
	@Autowired
    private ReceivingService receivingService;

    @RequestMapping(value = "/" )  
    public String index() throws Exception{  
        return "hongbao";  
    } 

    //	暴力测试手机号
    @RequestMapping(value = "/violence" , method = RequestMethod.POST)
    @ResponseBody
    public String violence(@RequestParam (value="url", required = false) String url) throws Exception{
    	//hongbaoService.randomViolenceRemain(url);//领红包
    	hongbaoService.randomViolence(url);//刷次数
        return "1";
    }

	@RequestMapping(value = "/getHongbao", method = RequestMethod.POST)
	@ResponseBody
    public String getHongbaoRemain(@RequestParam (value="phoneNum", required = false) String phoneNum,
    		@RequestParam (value="url", required = false) String url) throws Exception {
		String message = hongbaoService.getHongbao(phoneNum,url);
        return message;  
    }
    
    @RequestMapping(value = "/getAdvertising" )
    @ResponseBody
    public String getAdvertising() throws Exception{  
        return hongbaoService.getAdvertising();
    }

    @RequestMapping(value = "/getHongbao2", method = RequestMethod.POST)
    @ResponseBody
    public String getHongbao(@RequestParam (value="phoneNum", required = false) String phoneNum,
                                   @RequestParam (value="url", required = false) String url)throws Exception {
        String message = receivingService.getHongbao(phoneNum,url);
        return message;
    }

}