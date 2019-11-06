package com.bluesgao.api.gateway.core.handler.inbound;

import com.bluesgao.api.gateway.core.protocol.http.RequestParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class AuthenticationHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthenticationHandler channelRead");
        super.channelRead(ctx, msg);
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            HttpHeaders headers = request.headers();
            log.info("headers:{}", headers);
            HttpMethod method = request.method();
            log.info("method:{}", method);

            handleHttpRequest(ctx,request);

            RequestParser requestParser = new RequestParser((FullHttpRequest) msg);
            Map<String, String> requestMap = requestParser.parse();
            log.info("requestMap:{}", requestMap);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
        if (req.method() == GET) { // 处理get请求

            QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());
            Map<String, List<String>> parame = decoder.parameters();
            List<String> q = parame.get("q"); // 读取从客户端传过来的参数
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
            if (postData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                Attribute attribute = (Attribute) postData;
                question = attribute.getValue();
                System.out.println("q:" + question);
            }
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("AuthenticationHandler exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("AuthenticationHandler exceptionCaught");
        super.channelReadComplete(ctx);
    }
}
