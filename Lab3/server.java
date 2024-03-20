package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class server {
	private static final Logger LOGGER = Logger.getLogger(server.class.getName());
	private final int port;

	public server(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			LOGGER.info("Serwer uruchomiony na porcie " + port + ". Oczekiwanie na klientów...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				LOGGER.info("Nowe połączenie z klientem: " + clientSocket.getInetAddress());

				ClientHandler clientHandler = new ClientHandler(clientSocket);
				Thread clientThread = new Thread(clientHandler);
				clientThread.start();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Błąd serwera: " + e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		server serv = new server(port);
		serv.start();
	}
}
