package com.mark.config;

import com.mark.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerConfig implements CommandLineRunner, DisposableBean {

    private ChannelFuture future;
    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;
    private final int port = 8255;
    private final String host = "127.0.0.1";

    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            this.future = bootstrap.bind(host, port).syncUninterruptibly();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
//            future.channel().closeFuture().sync();
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    @Override
    public void destroy() throws Exception {
        // future表示服务器绑定到端口并准备接受连接的异步操作
        // isDone()用于判断future这个操作完成与否，
        if (future != null && !future.isDone()) {
            // closeFuture()能够返回一个特殊的future（表示关闭操作），sync()或syncUninterruptibly()用于阻塞当前线程，直到操作完成
            future.channel().closeFuture().syncUninterruptibly();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
