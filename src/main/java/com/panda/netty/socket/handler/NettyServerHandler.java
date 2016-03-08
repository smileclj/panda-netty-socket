package com.panda.netty.socket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import com.panda.netty.socket.common.ChannelManager;
import com.panda.netty.socket.common.ClientUser;
import com.panda.netty.socket.message.Message;
import com.panda.netty.socket.message.ins.LoginMessage;
import com.panda.netty.socket.util.CtxUtil;

public class NettyServerHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
	private ChannelManager channelManager;

	public NettyServerHandler(ChannelManager channelManager) {
		this.channelManager = channelManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message<Object> message = (Message<Object>) msg;
		Object messageBody = message.getBody();
		if (messageBody instanceof LoginMessage) {
			LoginMessage ms = (LoginMessage) messageBody;
			ClientUser client = new ClientUser(ms.getUserId(), ms.getUserName(), "");
			channelManager.addChannelContext(ctx, client);
		}
		logger.info("server channel read->messageId:{}", message.getMessageId());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("server channelInactive-->clientId:{}", CtxUtil.getClientId(ctx));
		super.channelInactive(ctx);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.info("server close-->clientId:{}", CtxUtil.getClientId(ctx));
		super.close(ctx, promise);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("server channelActive-->clientId:{}", CtxUtil.getClientId(ctx));
		super.channelActive(ctx);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.info("server disconnect-->clientId:{}", CtxUtil.getClientId(ctx));
		super.disconnect(ctx, promise);
	}

}
