package com.bluesgao.api.gateway.core.handler.inbound;

import com.bluesgao.api.gateway.core.protocol.http.RequestParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;

@Slf4j
public class AuthenticationHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        log.info("AuthenticationHandler channelRead0");
            HttpHeaders headers = fullHttpRequest.headers();
            log.info("headers:{}", headers);
            HttpMethod method = fullHttpRequest.method();
            log.info("method:{}", method);

            handleHttpRequest(channelHandlerContext,fullHttpRequest);

            RequestParser requestParser = new RequestParser(fullHttpRequest);
            Map<String, String> requestMap = requestParser.parse();
            log.info("requestMap:{}", requestMap);

    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (req.method() == GET) { // 处理get请求

            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            Map<String, List<String>> parms = decoder.parameters();
            List<String> q = parms.get("data"); // 读取从客户端传过来的参数
            String question = q.get(0);
            if (question != null && !question.equals("")) {
                System.out.println("r :" + question);
            }
        }else if (req.method() == POST) { // 处理POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), req);
            InterfaceHttpData postData = decoder.getBodyHttpData("username");
            // 读取从客户端传过来的参数
            String question = "";
            if (postData!=null && postData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                Attribute attribute = (Attribute) postData;
                question = attribute.getValue();
                System.out.println("q:" + question);
            }
            return;
        }
    }
}
