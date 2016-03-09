package com.panda.netty.socket.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panda.netty.socket.common.ChannelManager;
import com.panda.netty.socket.common.ClientUser;
import com.panda.netty.socket.message.Message;
import com.panda.netty.socket.message.ins.LoginMessage;
import com.panda.netty.socket.util.CtxUtil;

@Sharable
public class NettyServerHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
	private ChannelManager channelManager;

	public NettyServerHandler(ChannelManager channelManager) {
		this.channelManager = channelManager;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 客户端连接上服务端
		logger.info("server channelActive-->clientId:{}", CtxUtil.getClientId(ctx));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 客户端与服务端断开连接
		logger.info("server channelInactive-->clientId:{}", CtxUtil.getClientId(ctx));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 服务端接收客户端数据
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
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 服务端读取客户端数据完成
		logger.info("server channelReadComplete-->clientId:{}", CtxUtil.getClientId(ctx));
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// ctx抛出异常
		logger.info("server exceptionCaught-->clientId:" + CtxUtil.getClientId(ctx), cause);
		ctx.close();
	}
}
