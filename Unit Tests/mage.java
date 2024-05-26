package org.example;

public class mage {
		private String name;
		private int level;

	public mage(String name, int level){
		this.name = name;
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String toString(){
		return("Mage => Name: " + this.name + " Level: " + this.level);
	}
	public String strLevel(){
		return Integer.toString(level);
	}
}
