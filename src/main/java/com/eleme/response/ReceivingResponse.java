package com.eleme.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ReceivingResponse {

    private Long id;
    private String urlKey;
    private String url;
    private String UserId;
    private BigDecimal price;
    private String message;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

}
