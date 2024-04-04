package org.example;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Tower {
	@Id
	private String name;
	private int height;

	@OneToMany(mappedBy = "tower", cascade = CascadeType.REMOVE)
	private List<Mage> mages;

	public Tower(String name, int height){
		this.name = name;
		this.height = height;
		this.mages = new LinkedList<>();
	}
	public void set_name(String name) {
		this.name = name;
	}
	public void set_height(int i) {
		this.height = i;
	}
	public String get_name(){
		return this.name;
	}
	public int  get_height(){
		return this.height;
	}
	public  List<Mage> get_mages(){
		return this.mages;
	}

	public void removeWithMages(EntityManager em, EntityTransaction tx ) {
		try {
			tx.begin();
			em.remove(this);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
	}
}