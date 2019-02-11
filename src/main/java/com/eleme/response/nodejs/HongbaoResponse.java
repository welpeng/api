package com.eleme.response.nodejs;

import lombok.Data;

@Data
public class HongbaoResponse<T> {
    public int code;
    public String message;
    public T data;
}
