package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Task {
	private final Queue<String> tasks = new LinkedList<>();
	private final Queue<Integer> data = new LinkedList<>();

	public synchronized void addTask(String task) {
		this.tasks.add(task);
		notify();
	}
	public synchronized void addData(Integer data) {
		this.data.add(data);
	}

	public synchronized String getTask() throws InterruptedException {
		while (tasks.isEmpty()) {
			wait();
		}
		return tasks.poll();
	}

	public synchronized Integer getData() throws InterruptedException {
		while (this.data.isEmpty()) {
			wait();
		}
		return this.data.poll();
	}
}
