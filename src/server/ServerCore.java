package server;

public class ServerCore {

	public static void main(String[] args) {

		int i = 0;
		while (i < 5) {
			try {

				int port = 2654;
				Runnable socketTh = new SocketThread(port);
				Thread th = new Thread(socketTh);
				th.start();

				th.join();
				System.out.println(i);
			} catch (InterruptedException e) {
				System.out.println("Thread foi interrompida");
			}

			i++;

		}
	}
}