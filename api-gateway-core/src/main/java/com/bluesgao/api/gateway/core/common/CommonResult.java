package com.bluesgao.api.gateway.core.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<V> implements Serializable {
    private int code;
    private String message;
    private V result;
}
