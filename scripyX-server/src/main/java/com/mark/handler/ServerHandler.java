package com.mark.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("-----进入channelActive-----");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("-----进入channelInactive-----");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到客户端发送的消息：{}", System.currentTimeMillis());
        String receivedMsg = "";
        if (msg instanceof String) {
            receivedMsg = (String) msg;
        } else if (msg instanceof ByteBuf) {
            receivedMsg = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        } else {
            //TODO
        }
        log.info("收到客户端发送的消息：{}", receivedMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
