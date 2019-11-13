package com.bluesgao.api.gateway.core.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.bluesgao.api.gateway.core.common.CommonResult;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  HTTP响应构造器, 支持application/json
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBuilder {
    private ChannelHandlerContext ctx;
    private CommonResult result;

    public void jsonReponse() {
        // 1.设置响应
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(JSONObject.toJSONString(result), CharsetUtil.UTF_8));

        resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");

        // 2.发送
        // 注意必须在使用完之后，close channel
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }
}
