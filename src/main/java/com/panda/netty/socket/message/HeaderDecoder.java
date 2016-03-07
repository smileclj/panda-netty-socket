package com.panda.netty.socket.message;

import java.util.Date;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HeaderDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		if (buf.readableBytes() < Message.HEAD_LENGTH) {
			return;
		}
		buf.markReaderIndex();
		int length = buf.readInt();
		short version = buf.readShort();
		int command = buf.readInt();
		byte[] messageIdBytes = new byte[32];
		String messageId = new String(messageIdBytes);
		Date createTime = new Date(buf.readLong());

		if (buf.readableBytes() < length) {
			buf.resetReaderIndex();
			return;
		}

		Message<Object> message = new Message<Object>(length, version, command, messageId, createTime);
		byte[] bs = new byte[length - Message.HEAD_LENGTH];
		buf.readBytes(bs);
		message.decodeBody(bs);
		out.add(message);
	}
}