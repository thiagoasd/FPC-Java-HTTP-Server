package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread implements Runnable {

	int port;
	
	public SocketThread(int port) {
		this.port = port;
	}
	
	
	
	
	@Override
	public void run() {
		try {

			// Cria socket e espera
			ServerSocket socket = new ServerSocket(port);
			Socket server = socket.accept();
			System.out.println("Socket recebeu conexão");

			// Lê até receber o final da mensagem
			// (toda msg deve acabar com bye)
			BufferedReader inc = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream());
			String req, method, uri, data = "";
			req = method = uri = data;
				
				// Le a 1 linha da requisicao e a parsa
				req = inc.readLine(); // requisição
				String[] temp = req.split(" ");
				if (temp.length == 3) {
					method = temp[0];
					System.out.println("metodo = " + method);
					uri = temp[1].substring(1, temp[1].length());
				} else {
					System.err.println("Requisição malfeita");
					System.exit(1);
				}

				
				// Checa se tem headers e os poe em um map
				while (inc.ready() && !(inc.readLine()).equals("")) {
				}

				// Checa se tem data e o poe em uma string
				while (inc.ready()) {
				}

			// Respondendo
			// tem que mandar o "cabecalho" antes

			if (method.equals("GET")) {
				System.out.println();
				System.out.println("MODO GET");
				File arq = new File(uri);

				if (arq.exists()) {
					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: text/html");
					out.println("Content-Length: " + arq.length());
					out.flush();
					out.println();
					out.flush();
					System.out.println(arq.length());
					BufferedInputStream d = new BufferedInputStream(new FileInputStream(arq));
					BufferedOutputStream outStream = new BufferedOutputStream(server.getOutputStream());
					byte buffer[] = new byte[1024];
					int read;
					while ((read = d.read(buffer)) != -1) {
						outStream.write(buffer, 0, read);
						outStream.flush();
					}

					out.flush();
					d.close();
					outStream.close();
				} else {

					out.println("HTTP/1.1 404 FILE NOT FOUND");
					out.flush();
				}

			} else {
				String response = "200 OK";

				out.println();
				out.println(response);
				out.flush();
				out.println();
				out.flush();
			}
			out.close();
			inc.close();
			socket.close();
		} catch (IOException e) {
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

