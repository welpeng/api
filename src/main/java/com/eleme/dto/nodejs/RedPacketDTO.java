package com.eleme.dto.nodejs;

import lombok.Data;

import java.util.List;


@Data
public class RedPacketDTO {

    private RedPacketResultDTO result;
    private List<CookieUseStatusDTO> cookies;
    
}
