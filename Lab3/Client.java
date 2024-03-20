package org.example;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

	public static void main(String[] args) {
		String serverAddress = "localhost";
		int serverPort = 8080;

		try {
			Socket socket = new Socket(serverAddress, serverPort);

			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			String messageToSend = "Hello, server!";
			outputStream.writeUTF(messageToSend);

			outputStream.close();
			socket.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Błąd klienta: " + e.getMessage(), e);
		}
	}
}
