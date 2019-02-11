package com.eleme.dto.nodejs;

import lombok.Data;

@Data
public class ResultDTO<T> {

    public int code;
    public String message;
    public T data;

}
