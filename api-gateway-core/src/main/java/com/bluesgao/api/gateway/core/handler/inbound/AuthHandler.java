package com.bluesgao.api.gateway.core.handler.inbound;

import com.alibaba.fastjson.JSON;
import com.bluesgao.api.gateway.core.common.CommonResult;
import com.bluesgao.api.gateway.core.common.ResultCodeEnum;
import com.bluesgao.api.gateway.core.protocol.http.RequestParser;
import com.bluesgao.api.gateway.core.protocol.http.ResponseBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 请求鉴权处理器
 */
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        log.debug("AuthHandler channelRead0");
        RequestParser requestParser = new RequestParser(request);
        Map<String, String> requestMap = requestParser.parse();
        log.debug("requestMap:{}", JSON.toJSONString(requestMap));

        //todo 鉴权处理
        //1,通过appid到cache中获取appinfo
        String appId = requestMap.get("appId");
        String accessToken = requestMap.get("accessToken");
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(accessToken)) {
            log.debug("鉴权不通过appId is null or accessToken is null");
            // 响应
            CommonResult result = new CommonResult().buildError(ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getCode(), ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getMessage());
            new ResponseBuilder(ctx, result).jsonReponse();
        }
        getAppInfo(appId);
        //2,通过对比请求中的appid和accessToken

        boolean flag = false;

        if (!flag) {
            log.debug("鉴权不通过");
            // 响应
            CommonResult result = new CommonResult().buildError(ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getCode(), ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getMessage());
            new ResponseBuilder(ctx, result).jsonReponse();
        } else {
            log.debug("鉴权通过");

        }
    }

    private void getAppInfo(String appId) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //在发生异常时，记录错误并关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
