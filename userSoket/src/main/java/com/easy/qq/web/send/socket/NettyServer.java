package com.easy.qq.web.send.socket;

import com.easy.qq.web.send.service.MySocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class NettyServer {
    /**
     * socketServer 端口号
     */
    @Value("${netty.server.port}")
    private String nettyServerPort;

    @Resource
    private MySocketHandler mySocketHandler;

    public void nettyServerStart() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
            bootstrap.group(boos, work).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 64 * 1024, true, true, 1000L));
                            ch.pipeline().addLast(mySocketHandler);
                        }
                    });
            log.info("nettyServer:启动成功");
            ChannelFuture future = bootstrap.bind(Integer.valueOf(nettyServerPort == null ? "8090" : nettyServerPort)).sync();
            future.channel().closeFuture().sync();
            System.out.println("nettyServer:关闭成功");
        } catch (Exception e) {
            log.error("nettyServer异常:", e);
        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.nettyServerStart();
    }
}
