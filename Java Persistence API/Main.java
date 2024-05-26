package org.example;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

public class Main {
	public static void display_list(List<Tower> towers){
		for (Tower tmp: towers) {
			System.out.println("Tower: " + tmp.get_name() + ", Height: " + tmp.get_height());
			List<Mage> mages = tmp.get_mages();
			for (Mage mage : mages) {
				System.out.println(" - Mage: " + mage.get_name() + ", Level: " + mage.get_level());
			}
		}
		System.out.println("\n");
	}
	public static List<Mage> getMagesWithLevelGreaterThan(EntityManager em, int level) {
		TypedQuery<Mage> query = em.createQuery("SELECT m FROM Tower t JOIN t.mages m", Mage.class);
		List<Mage> mages = query.getResultList();
		return mages;
	}

	public static List<Tower> getTowersWithHeightLessThan(EntityManager em, int height) {
		TypedQuery<Tower> query = em.createQuery("SELECT t FROM Tower t WHERE t.height < :height", Tower.class);
		query.setParameter("height", height);
		return query.getResultList();
	}

	public static List<Mage> getMagesWithLevelHigherThanFromTower(EntityManager em, String towerName, int level) {
		TypedQuery<Mage> query = em.createQuery("SELECT m FROM Mage m WHERE m.tower.name = :towerName AND m.level > :level", Mage.class);
		query.setParameter("towerName", towerName);
		query.setParameter("level", level);
		return query.getResultList();
	}
	public static void main(String args[]){
		Tower tower = new Tower("casual tower", 100);
		Tower tower2 = new Tower("strong tower", 2137);

		Mage mage1 = new Mage("MageG", 10, tower);
		Mage mage2 = new Mage("JustMage", 20, tower);
		Mage mage3 = new Mage("Strong Mage", 50, tower2);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("name_pu");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(tower);
		em.persist(tower2);
		tx.commit();

		TypedQuery<Tower> query = em.createQuery("SELECT t FROM Tower t", Tower.class);
		List<Tower> towers = query.getResultList();
		display_list(towers);
		//------------------------------------------------------------------------------------------------------------------------------
		tx.begin();
		//usuwanie jednostki
		tower.get_mages().remove(mage1);
		//dodawanie jednostki
		Mage mage4 = new Mage("Magnifik", 15,tower);
		Mage mage5 = new Mage("Strong James", 1589,tower2);
		tx.commit();
		towers = query.getResultList();
		display_list(towers);
		//------------------------------------------------------------------------------------------------------------------------------
		List<Mage> magesWithLevelGreaterThan = getMagesWithLevelGreaterThan(em, 19);
		System.out.println("Mages with level greater than 19:");
		for (Mage mage : magesWithLevelGreaterThan) {
			System.out.println(" - Mage: " + mage.get_name() + ", Level: " + mage.get_level());
		}
		System.out.println("\n");

		List<Tower> towersWithHeightLessThan2000 = getTowersWithHeightLessThan(em, 2000);
		System.out.println("Towers with height less than 2000:");
		for (Tower t : towersWithHeightLessThan2000) {
			System.out.println("Tower: " + t.get_name() + ", Height: " + t.get_height());
		}
		System.out.println("\n");

		List<Mage> magesWithLevelHigherThan30FromTower = getMagesWithLevelHigherThanFromTower(em, "strong tower", 30);
		System.out.println("Mages with level higher than 30 from 'strong tower':");
		for (Mage mage : magesWithLevelHigherThan30FromTower) {
			System.out.println(" - Mage: " + mage.get_name() + ", Level: " + mage.get_level());
		}
		System.out.println("\n");


		//------------------------------------------------------------------------------------------------------------------------------

		tower.removeWithMages(em, tx);
		towers = query.getResultList();
		display_list(towers);

		em.close();
		emf.close();
	}
}
