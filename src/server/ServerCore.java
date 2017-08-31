package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCore {

	public static void main(String[] args) throws IOException {
		int port = 2654;
		ServerSocket Ssock = new ServerSocket(port);
		
		while (true) {

			Socket sock = Ssock.accept();
			Runnable socketTh = new SocketThread(sock);
			Thread th = new Thread(socketTh);
			th.start();
		}
	}
}