package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerCore {

	public static void main(String[] args) {
		// TODO separar as partes do request e criar um log

		try {
			ServerSocket socket = new ServerSocket(2654);
			Socket server = socket.accept();
			System.out.println("Socket aberto");

			Scanner inc = new Scanner(server.getInputStream());
			PrintWriter out = new PrintWriter(server.getOutputStream());

			while (inc.hasNextLine()) {
				String request = inc.nextLine();
				System.out.println(request);
			}

			//tem que mandar o "cabecalho" antes
			String response = "teste";
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
