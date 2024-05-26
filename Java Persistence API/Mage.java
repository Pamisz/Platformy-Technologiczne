package org.example;

import jakarta.persistence.*;

@Entity
public class Mage {
	@Id
	private String name;
	private int level;

	@ManyToOne
	private Tower tower;

	public Mage(String name, int level, Tower tower){
		this.name = name;
		this.level = level;
		this.tower = tower;
		tower.get_mages().add(this);
	}
	public void set_name(String name) {
		this.name = name;
	}
	public void set_level(int i){
		this.level = i;
	}
	public void set_tower(Tower tower) {
		this.tower = tower;
	}
	public String get_name(){
		return this.name;
	}
	public int get_level(){
		return this.level;
	}
	public Tower get_tower(){
		return this.tower;
	}
}


