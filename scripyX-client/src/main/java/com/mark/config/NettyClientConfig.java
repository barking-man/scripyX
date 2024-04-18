package com.mark.config;

import com.mark.handler.VideoDataHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyClientConfig implements CommandLineRunner, DisposableBean {

    private static Channel channel;
    private NioEventLoopGroup group;

    private final String host = "127.0.0.1";
    private final int port = 8255;

    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new VideoDataHandler());
                        }
                    });
            channel = bootstrap.connect(host, port).syncUninterruptibly().channel();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    @Override
    public void destroy() throws Exception {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully().sync();
        }
    }

    public static void sendMsg(String msg) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(msg);
        } else {
            // TODO 处理通道未就绪的情况
        }
    }
}
