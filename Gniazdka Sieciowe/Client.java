package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	public Client(String address, int port) throws IOException {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			String ready = (String) in.readObject();
			System.out.println("Server says: " + ready);

			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the number of messages to send: ");
			int n = scanner.nextInt();
			out.writeObject(n);
			out.flush();

			ready = (String) in.readObject();
			System.out.println("Server says: " + ready);

			for (int i = 0; i < n; i++) {
				String mess = "";
				while(mess.equals("")){
					mess = scanner.nextLine();
				}
				Message message = new Message(i, mess);
				out.writeObject(message);
				out.flush();
			}

			String finished = (String) in.readObject();
			System.out.println("Server says: " + finished);

			} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		String serverAddress = "localhost";
		int serverPort = 8080;
		Client cli = new Client(serverAddress, serverPort);
	}
}
