package com.bluesgao.api.gateway.core.common;

/**
 * 通用返回码
 */
public enum ResultCodeEnum {
    SUCCESS(0, "成功"),
    FAIL(9999, "错误"),
    //app相关错误码
    ERROR_APP_ILLEGAL(1000, "appID非法"),
    APP_ERROR_STATUS(1001, "appID状态异常"),
    //鉴权相关错误码
    APP_ERROR_AUTH_PARAMS(2000, "鉴权参数不全"),
    APP_ERROR_ACCESS_TOKEN(2001, "授权码过期"),
    APP_ERROR_REFRESH_TOKEN(2002, "刷新码过期");
    //商品相关错误码
    //类目相关错误码
    //订单相关错误码

    private Integer code = null;
    private String message = null;

    public static ResultCodeEnum getEnum(Integer code) {
        if (null == code) {
            return null;
        } else {
            ResultCodeEnum[] arr = values();
            int len = arr.length;

            for (int i = 0; i < len; ++i) {
                ResultCodeEnum a = arr[i];
                if (a.code.equals(code)) {
                    return a;
                }
            }

            throw new IllegalArgumentException("No enum code \'" + code + "\'. " + ResultCodeEnum.class);
        }
    }

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
