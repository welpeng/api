package com.eleme.service;


import com.eleme.entity.Alt;
import com.eleme.entity.Receiving;

import java.util.List;

public interface ReceivingService {
    public String getHongbao(String phoneNum, String url);
    String dispatch(Receiving receiving);
    String receive(Receiving receiving, List<Alt> cookies);

}
