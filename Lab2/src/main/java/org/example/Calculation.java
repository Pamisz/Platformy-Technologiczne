package org.example;
import java.util.Random;

public class Calculation implements Runnable {
	private final Task task;
	private final Result result;

	public Calculation(Task task, Result result) {
		this.task = task;
		this.result = result;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String tas = task.getTask();
				if (tas.equals("Czy liczba pierwsza?")){
					Integer buff = task.getData();
					System.out.print("\nAnaliza liczby:" + buff + "\n");
					Thread.sleep(2000);
					System.out.print("\nWykonywanie obliczeń...\n");
					Thread.sleep(5000);
					String res = "\nWynik obliczeń dla zadania: " + tas +" \nDane: " + buff + " \nWynosi: "+ isPrime(buff) + "\n";
					result.addResult(res);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static int modPow(int base, int exponent, int modulus) {
		int result = 1;
		while (exponent > 0) {
			if (exponent % 2 == 1) {
				result = (result * base) % modulus;
			}
			base = (base * base) % modulus;
			exponent /= 2;
		}
		return result;
	}

	public static boolean isPrime(int n) {
		if (n == 2 || n == 3) {
			return true;
		}
		if (n % 2 == 0 || n ==1) {
			return false;
		}

		int s = n - 1;
		int r = 0;
		while (s % 2 == 0) {
			r++;
			s /= 2;
		}

		int k = 5;
		Random random = new Random();
		for (int i = 0; i < k; i++) {
			int a = random.nextInt(n - 2) + 2;
			int x = modPow(a, s, n);

			if (x == 1 || x == n - 1) {
				continue;
			}

			for (int j = 0; j < r - 1; j++) {
				x = modPow(x, 2, n);
				if (x == n - 1) {
					break;
				}
			}
			if (x != n - 1) {
				return false;
			}
		}
		return true;
	}
}
