package org.example;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int numThreads = Integer.parseInt(args[0]);

		Task task = new Task();
		Result result = new Result();

		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(new Calculation(task, result));
			threads[i].start();
		}

		Scanner scanner = new Scanner(System.in);
		boolean exitFlag = false;
		while (!exitFlag) {
			System.out.println("\nPodaj zadanie: ");
			String tmp = scanner.nextLine();
			while (tmp.isEmpty()){
				tmp = scanner.nextLine();
			}
			int data = 0;

			if (tmp.equals("exit")) {
				exitFlag = true;
			} else {
				task.addTask(tmp);
				if (tmp.equals("Czy liczba pierwsza?")){
					System.out.print("Podaj dane:");
					data = scanner.nextInt();
				}
				task.addData(data);
			}

		}

		result.getAll();
		for (Thread thread : threads) {
			thread.interrupt();
		}
		scanner.close();
		System.out.println("\nProgram zakończony.");
	}
}
//Czy liczba pierwsza?
//103841		tak
// 94841			tak
// 89443			tak
// 63691			tak
// 43541			tak
