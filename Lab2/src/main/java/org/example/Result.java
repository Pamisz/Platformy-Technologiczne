package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Result {
	private final Queue<String> results = new LinkedList<>();

	public synchronized void addResult(String result) {
		results.add(result);
		System.out.print(result);
	}

	public synchronized String getResult() {
		return results.poll();
	}
}
