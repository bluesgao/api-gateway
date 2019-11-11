package com.bluesgao.api.gateway.core.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<V> implements Serializable {
    private int code;
    private String message;
    private V result;
}
