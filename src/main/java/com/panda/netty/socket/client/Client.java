package com.panda.netty.socket.client;

import com.panda.netty.socket.message.Message;
import com.panda.netty.socket.message.ins.LoginMessage;
import com.panda.netty.socket.util.UUIDUtil;

public class Client {
	private InClient inClient;

	public Client(String serverHost, int serverPort) {
		inClient = new InClient(serverHost, serverPort);
	}

	public void connect() {
		inClient.connect();
	}

	@SuppressWarnings("rawtypes")
	public void sendMessage(Message message) {
		inClient.sendMessage(message);
	}

	public void close() {
		inClient.close();
	}

	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 9100);
		client.connect();
		LoginMessage lmBody = new LoginMessage();
		lmBody.setUserId("123");
		lmBody.setUserName("小明");
		Message<LoginMessage> msLoginMessage = new Message<LoginMessage>(UUIDUtil.create(), lmBody);
		msLoginMessage.encodeBody();

		Client client2 = new Client("127.0.0.1", 9100);
		client2.connect();
		LoginMessage lmBody2 = new LoginMessage();
		lmBody2.setUserId("456");
		lmBody2.setUserName("小红");
		Message<LoginMessage> msLoginMessage2 = new Message<LoginMessage>(UUIDUtil.create(), lmBody2);
		msLoginMessage2.encodeBody();

		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				while (true) {
					client.sendMessage(msLoginMessage);
					 try {
					 Thread.sleep(5000);
					 } catch (InterruptedException e) {
					 }
				}
			}
		};

		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				while (true) {
					client2.sendMessage(msLoginMessage2);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();

	}
}
