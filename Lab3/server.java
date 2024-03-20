package org.example;

import java.io.*;
import java.net.*;

public class server {
	private ServerSocket server = null;

	public server(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			System.out.println("Waiting for a clients ...");

			while (true) {
				Socket clientSocket = server.accept();
				System.out.println("New client connected: " + clientSocket.getInetAddress());

				ClientHandler clientHandler = new ClientHandler(clientSocket);
				Thread clientThread = new Thread(clientHandler);
				clientThread.start();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		int port = 8080;
		server serv = new server(port);
	}
}
