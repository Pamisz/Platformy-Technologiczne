package org.example;

public class Calculation implements Runnable {
	private final Task task;
	private final Result result;

	public Calculation(Task task, Result result) {
		this.task = task;
		this.result = result;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				Integer buff = task.getData();
				String tas = task.getTask();
				if (tas.equals("Czy liczba pierwsza?")){
					String res = "\nWynik obliczeń dla zadania: " + tas +" \nDane: " + buff + " \nWynosi: "+ isPrime(buff) + "\n";
					result.addResult(res);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static boolean isPrime(int n) {
		if (n == 2 || n == 3) {
			return true;
		}
		if (n  == 0 || n ==1) {
			return false;
		}
		for (int  i = 2; i<n ;i++){
			if  (n % i == 0){
				return false;
			}
		}
		return true;
	}
}
