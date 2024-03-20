package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
	private final Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try (
				ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
		) {
			while (true) {
				Object receivedObject = inputStream.readObject();

				if (receivedObject instanceof String receivedMessage) {
					LOGGER.info("Otrzymano wiadomość od klienta " + clientSocket.getInetAddress() + ": " + receivedMessage);
				} else {
					LOGGER.warning("Otrzymano nieznany typ obiektu od klienta " + clientSocket.getInetAddress());
				}
			}

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Błąd obsługi klienta: " + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}

	private void closeConnection() {
		try {
			clientSocket.close();
			LOGGER.info("Zamknięto połączenie z klientem: " + clientSocket.getInetAddress());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Błąd podczas zamykania połączenia: " + e.getMessage(), e);
		}
	}
}
