package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerCore {

	public static void main(String[] args) {
		int port = 2654;
		// TODO separar as partes do request, criar um log e descobrir como conseguir
		// fazer com que o reader não de block

		try {

			// Cria socket e espera
			ServerSocket socket = new ServerSocket(port);
			Socket server = socket.accept();
			System.out.println("Socket recebeu conexão");

			// Lê até receber o final da mensagem
			// (toda msg deve acabar com bye)
			Scanner inc = new Scanner(server.getInputStream());
			PrintWriter out = new PrintWriter(server.getOutputStream());
			String end = "bye";
			while (inc.hasNextLine()) {
				String request = inc.nextLine();
				if (request.contains(end)) {
					break;
				}
				System.out.println(request);
			}

			// Respondendo
			// tem que mandar o "cabecalho" antes
			String response = "bye";
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html");
			out.println("Content-Length: " + response.length());
			out.println();
			out.println(response);
			out.flush();
			out.println();
			out.flush();

			out.close();
			inc.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getRequestType(String request) {
		String requestType = "";

		if (request.contains("GET")) {
			requestType = "GET";
		}
		if (request.contains("POST")) {
			requestType = "POST";
		}
		if (request.contains("PUT")) {
			requestType = "PUT";
		}
		if (request.contains("DELETE")) {
			requestType = "DELETE";
		}

		return requestType;
	}

}
