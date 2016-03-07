package com.panda.netty.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.panda.netty.socket.message.Message;

@SuppressWarnings("rawtypes")
public class HeaderEncoder extends MessageToByteEncoder<Message> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf buf) throws Exception {
		logger.info("head encode");
		buf.writeInt(message.getLength());
		buf.writeShort(message.getVersion());
		buf.writeInt(message.getCommand());
		buf.writeBytes(message.getMessageId().getBytes());
		buf.writeLong(message.getCreateTime().getTime());
		buf.writeBytes(message.encodeBody());
	}
}
