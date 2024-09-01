package com.mark.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class VideoFrameDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 创建或获取累积缓冲区（这里简化处理，直接创建新的ByteBuf）
        ByteBuf buffer = ctx.alloc().buffer();
        // 假设的逻辑：将接收到的数据添加到累积缓冲区中（实际可能更复杂）
        buffer.writeBytes(in);

        // TODO 从累积缓冲区中解析数据

        // TODO 如果累积缓冲区中还有剩余数据，但不足以构成一个完整的NALU，则保留这些数据

        // 已处理完所有累积缓冲区中的数据，则释放
        buffer.release();
    }

    // 提取ByteBuf中的NAL单元
    private List<byte[]> extractNalUnits(ByteBuf byteBuf) {
        List<byte[]> nalUnits = new ArrayList<>();
        int length = byteBuf.readableBytes();
        int position = 0;

        while (position < length) {
            int nalStart = findNalStart(byteBuf, position, false);
            // 未找到分隔符
            if (nalStart < 0) {
                break;
            }
            int nalNextStart = -1;
            if (nalStart + 4 < length) {
                nalNextStart = findNalStart(byteBuf, nalStart + 4, false);
            }
            int nalLength = 0;
            if (nalNextStart < 0) {
                // 不存在下一个分隔符
                nalLength = length - nalStart - (nalStart + 4 <= length ? 4 : 0);
            } else {
                // 存在下一个分隔符
                nalLength = nalNextStart - nalStart - 4;
            }

            // 提取NAL单元
            byte[] nalUnit = new byte[nalLength];
            byteBuf.getBytes(nalStart + (nalStart + 4 <= length ? 4 : 0), nalUnit);

            // 将NAL单元添加到列表
            nalUnits.add(nalUnit);

            // 更新位置，继续添加下一个NAL单元
            position = nalNextStart;
        }
        return nalUnits;
    }

    // 查找下一个NAL单元的起始位置

    /**
     *
     * @param byteBuf 输入数据
     * @param start 起始索引
     * @param isSimple 分隔符类型：{0, 0, 0, 1} 或者 {0， 1}（后者为简化版）
     * @return
     */
    private int findNalStart(ByteBuf byteBuf, int start, boolean isSimple) {
        byte[] pattern = isSimple ? new byte[]{0, 1} : new byte[]{0, 0, 0, 1};
        // 标准的NAL单元分隔符
        int patternLength = pattern.length;
        int maxLength = byteBuf.readableBytes() - patternLength + 1;

        for (int i = start; i < maxLength; i++) {
            boolean found = true;
            for (int j = 0; j < patternLength; j++) {
                if (byteBuf.getByte(i + j) != pattern[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        // 无分隔符
        return -1;
    }

    // 检查NAL单元是否使用Annex-B格式（包含完整的分隔符）
    private boolean isAnnexbNal(ByteBuf byteBuf, int nalStart) {
        return byteBuf.getByte(nalStart) == 0 && byteBuf.getByte(nalStart + 1) == 0 && byteBuf.getByte(nalStart + 2) == 0;
    }
}
