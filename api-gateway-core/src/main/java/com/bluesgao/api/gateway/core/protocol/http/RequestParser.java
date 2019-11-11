package com.bluesgao.api.gateway.core.protocol.http;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求参数解析器, 支持GET, POST
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestParser {
    private FullHttpRequest request;

    /**
     * 解析请求参数
     *
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     * @throws IOException
     */
    public Map<String, String> parse() throws Exception {
        HttpMethod method = request.method();
        Map<String, String> parmMap = new HashMap<>();
        if (HttpMethod.GET == method) {// 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());//获取get查询参数
            decoder.parameters().entrySet().forEach(entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        } else if (HttpMethod.POST == method) {// 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            if (request.content().isReadable()) {
                String dataJson = request.content().toString(CharsetUtil.UTF_8);
                parmMap.putAll(JSONObject.parseObject(dataJson, Map.class));
            }
            decoder.offer(request);//form表单
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                parmMap.put(data.getName(), data.getValue());
            }
        } else {
            // 不支持其它方法
            throw new Exception("HTTP请求只支持post和get方法，其它方法不支持");
        }
        return parmMap;
    }
}