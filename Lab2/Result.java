package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Result {
	private final Queue<String> results = new LinkedList<>();

	public synchronized void addResult(String result) {
		results.add(result);
	}

	public synchronized String getResult() {
		return results.poll();
	}

	public synchronized  void  getAll(){
		for (String tmp : results ){
			System.out.print(tmp);
		}
	}
}
