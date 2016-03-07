package com.panda.netty.socket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.panda.netty.socket.message.Message;

public class NettyServerHandler extends ChannelHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message<Object> message = (Message<Object>) msg;
		logger.info("server channel read->messageId:{}", message.getMessageId());
	}
}
