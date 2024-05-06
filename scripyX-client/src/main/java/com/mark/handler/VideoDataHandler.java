package com.mark.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VideoDataHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("客户端发送消息：{}", System.currentTimeMillis());
        ByteBuf byteBuf = Unpooled.copiedBuffer((String) msg, CharsetUtil.UTF_8);
        ctx.write(byteBuf, promise);
    }
}
