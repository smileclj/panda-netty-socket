package com.panda.netty.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panda.netty.socket.message.Message;
import com.panda.netty.socket.message.ins.LoginMessage;
import com.panda.netty.socket.util.UUIDUtil;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyClientHandler extends ChannelHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("client channel active");
		LoginMessage lmBody = new LoginMessage();
		lmBody.setUserId("123");
		Message<LoginMessage> msLoginMessage = new Message<LoginMessage>(UUIDUtil.create(), lmBody);
		msLoginMessage.encodeBody();
		ctx.writeAndFlush(msLoginMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

}
