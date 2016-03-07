package com.panda.netty.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HeaderEncoder()).addLast(new NettyClientHandler());
				}
			});
			ChannelFuture f = b.connect("127.0.0.1", 9100).sync();
			logger.info("client start");
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {

		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
