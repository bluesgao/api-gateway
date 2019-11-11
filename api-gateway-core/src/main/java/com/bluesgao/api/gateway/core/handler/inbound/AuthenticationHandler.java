package com.bluesgao.api.gateway.core.handler.inbound;

import com.alibaba.fastjson.JSON;
import com.bluesgao.api.gateway.core.protocol.http.RequestParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class AuthenticationHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        log.info("AuthenticationHandler channelRead0");
        RequestParser requestParser = new RequestParser(fullHttpRequest);
        Map<String, String> requestMap = requestParser.parse();
        log.info("requestMap:{}", JSON.toJSONString(requestMap));
    }
}
