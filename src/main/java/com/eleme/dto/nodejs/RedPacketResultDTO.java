package com.eleme.dto.nodejs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedPacketResultDTO {

    private String id;
    private String nickname;
    private BigDecimal price;
    private String date;
}
