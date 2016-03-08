package com.panda.netty.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panda.netty.socket.message.Message;

public class InClient {
	private static final Logger logger = LoggerFactory.getLogger(InClient.class);
	private String serverHost = "127.0.0.1";
	private int serverPort = 9100;
	private NettyClientHandler handler;

	private EventLoopGroup workGroup;
	private Bootstrap bootstrap;
	private Channel channel; // 客户端通信通道

	public InClient(String serverHost, int serverPort) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		handler = new NettyClientHandler();
		workGroup = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(workGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new HeaderEncoder()).addLast(handler);
			}
		});
	}

	public void connect() {
		ChannelFuture channelFuture = null;
		try {
			channelFuture = bootstrap.connect(serverHost, serverPort).sync();
			channel = channelFuture.channel();
			logger.info("client connect-->host:{},port:{}", serverHost, serverPort);
		} catch (InterruptedException e) {
			logger.error("client connect failed", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void sendMessage(Message message) {
		channel.writeAndFlush(message);
	}

	public void close() {
		workGroup.shutdownGracefully();
	}
}
