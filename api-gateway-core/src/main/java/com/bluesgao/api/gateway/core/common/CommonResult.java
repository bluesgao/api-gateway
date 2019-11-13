package com.bluesgao.api.gateway.core.common;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -4606232502008831307L;
    /**
     * 响应码
     */
    private String resultCode;
    /**
     * 响应信息
     */
    private String resultMsg;
    /**
     * 返回值
     */
    private T value;

    public static Builder custom() {
        return new Builder();
    }

    public static class Builder<T> {

        private String resultCode;

        private String resultMsg;

        private T value;


        public Builder<T> code(String resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        public Builder<T> info(String resultMsg) {
            this.resultMsg = resultMsg;
            return this;
        }

        public Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        public CommonResult<T> build() {
            return new CommonResult(this);
        }

    }

    public CommonResult() {

    }

    private CommonResult(Builder builder) {
        resultCode = builder.resultCode;
        resultMsg = builder.resultMsg;
        value = (T) builder.value;
    }

    public CommonResult<T> buildSuccess(T t) {
        this.resultCode = ResultCodeEnum.SUCCESS.getCode();
        this.resultMsg = ResultCodeEnum.SUCCESS.getMessage();
        this.value = t;
        return this;
    }

    public CommonResult<T> buildError(ResultCodeEnum resultCodeEnum) {
        this.resultCode = resultCodeEnum.getCode();
        this.resultMsg = resultCodeEnum.getMessage();
        return this;
    }

    public CommonResult<T> buildError(String resultCode, String message) {
        this.resultCode = resultCode;
        this.resultMsg = message;
        return this;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public T getValue() {
        return value;
    }
}
