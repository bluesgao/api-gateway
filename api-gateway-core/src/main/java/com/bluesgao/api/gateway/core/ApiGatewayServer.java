package com.bluesgao.api.gateway.core;

import com.bluesgao.api.gateway.core.handler.inbound.AuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiGatewayServer {
    static final int PORT = 8888;

    public void start(int port) {
        log.info("ApiGatewayServer start...");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.debug("initChannel...");
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            //ch.pipeline().addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            //ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast( new HttpObjectAggregator(65536));
                            //ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new AuthHandler());
                        }
                    });

            Channel ch = serverBootstrap.bind(port).sync().channel();
            log.info("ApiGatewayServer listening on port:" + port);
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ApiGatewayServer server = new ApiGatewayServer();
        server.start(PORT);
    }
}
