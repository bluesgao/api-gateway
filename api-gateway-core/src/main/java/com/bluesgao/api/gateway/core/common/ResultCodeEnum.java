package com.bluesgao.api.gateway.core.common;

/**
 * 通用返回码
 * 6位长度
 * 1位代表系统
 * 2-3位代表模块
 * 4-6位代码业务具体错误
 */
public enum ResultCodeEnum {
    SUCCESS("000000", "成功"),
    FAIL("999999", "错误"),
    //app相关错误码
    ERROR_APP_ILLEGAL("101001", "appID非法"),
    APP_ERROR_STATUS("101002", "appID状态异常"),
    //鉴权相关错误码
    APP_ERROR_AUTH_PARAMS("101003", "鉴权参数不全"),
    APP_ERROR_ACCESS_TOKEN("101004", "授权码过期"),
    APP_ERROR_REFRESH_TOKEN("101005", "刷新码过期");
    //商品相关错误码
    //类目相关错误码
    //订单相关错误码

    private String code = null;
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

    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
