package org.example;

import java.io.Serializable;
public class Message implements Serializable {
	private int number;
	private String content;

	public Message(int number, String content){
		this.number = number;
		this.content = content;
	}

	public void  set_number(int number){
		this.number = number;
	}

	public void set_content(String content){
		this.content = content;
	}

	public int get_number(){
		return this.number;
	}

	public String get_content(){
		return this.content;
	}
}