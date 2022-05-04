package com.girmiti.nexo.server.corelauncher;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.girmiti.nexo.netty.RequestListner;

public class ClientHandler {

	static Logger logger = Logger.getLogger(ClientHandler.class);

	protected Socket socket; 

	public ClientHandler(int port) {
		try {
			RequestListner nexofastListner = new RequestListner();
			nexofastListner.connectPort(port, 100, 100, 100);

		} catch (Exception e) {
			logger.error(e);
		}
	}
}
