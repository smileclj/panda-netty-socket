package com.panda.netty.socket;

import com.panda.netty.socket.common.InServer;
import com.panda.netty.socket.message.Message;

/**
 * 
 * @author chenlj
 * @Date 2016年3月5日 下午6:27:16
 */
public class Server {
	private InServer inServer;

	public Server(String serverName, int serverPort) {
		inServer = new InServer(serverName, serverPort);
	}

	public void start() {
		inServer.start();
	}

	public void sendMessage(String clientId, Message<Object> message) {
		inServer.sendMessage(clientId, message);
	}

	public void close() {
		inServer.close();
	}

	public static void main(String[] args) {
		Server server = new Server("服务1", 9100);
		server.start();
	}
}
