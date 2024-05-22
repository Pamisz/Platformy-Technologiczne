package org.example;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
	private final Socket clientSocket;
	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());

			out.writeObject("ready");
			out.flush();

			int n = (int) in.readObject();

			out.writeObject("ready for messages");
			out.flush();

			for (int i = 0; i < n; i++) {
				Message message = (Message) in.readObject();
				message.set_number(i+1);
				System.out.println("Received message number: " + message.get_number() + "  from client: " + message.get_content()) ;
			}

			out.writeObject("finished");
			out.flush();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}

	private void closeConnection() {
		try {
			clientSocket.close();
			System.out.println("Connection closed: " + clientSocket.getInetAddress());
		} catch (IOException e) {
			System.out.println("Error with closing connetcion: " + e.getMessage());
		}
	}
}
