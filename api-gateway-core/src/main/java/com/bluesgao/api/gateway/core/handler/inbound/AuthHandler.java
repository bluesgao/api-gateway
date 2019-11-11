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
        boolean flag = false;
        if (!flag) {
            log.debug("鉴权不通过");
            // 响应
            CommonResult<String> result = new CommonResult<>();
            result.setCode(ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getCode());
            result.setMessage(ResultCodeEnum.APP_ERROR_AUTH_PARAMS.getMessage());
            new ResponseBuilder(ctx, result).reponse();
        } else {
            log.debug("鉴权通过");

        }
    }
}
