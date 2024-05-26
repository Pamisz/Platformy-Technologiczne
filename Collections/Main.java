package org.example;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		String sorting = args[0];
		System.out.print("Rodzaj sortowania: " + sorting + "\n");

		//Dane
		Mage m1 = new Mage("Czaro", 1, 2.52, sorting);
		Mage m2 = new Mage("James", 2, 5.10, sorting);
		Mage m3 = new Mage("Jerry", 5, 8.25, sorting);
		Mage m4 = new Mage("Czaro", 9, 14.68, sorting);
		Mage m5 = new Mage("James", 2, 7.20, sorting);
		Mage m6 = new Mage("Jerry", 4, 5.10, sorting);
		Mage m7 = new Mage("Bartas", 7, 12.48, sorting);
		Mage m8 = new Mage("Juras", 8, 13.18, sorting);
		Mage m9 = new Mage("Carbi", 9, 14.94, sorting);
		Mage m10 = new Mage("Kresol", 10, 15.00, sorting);
		Mage m11 = new Mage("Bambi", 3, 9.64, sorting);
		Mage m12 = new Mage("Maniek", 6, 11.82, sorting);

		m10.add_apprentices(m9);
		m10.add_apprentices(m12);
		m10.add_apprentices(m4);
		m12.add_apprentices(m6);
		m12.add_apprentices(m5);
		m12.add_apprentices(m2);
		m12.add_apprentices(m1);
		m9.add_apprentices(m8);
		m9.add_apprentices(m7);
		m9.add_apprentices(m3);
		m3.add_apprentices(m11);

		System.out.print("\nPotomkowie:\n");
		m10.show_apprenticies(1);

		System.out.print("\nMapa:\n");
		 Map <Mage,Integer> m = m10.maping(sorting);
		for(Mage tmp : m.keySet()){
			System.out.println(tmp.toString() + "ma " + m.get(tmp)+" potomków");
		}
	}
}
//Rodzaje sortowania:
//1.naturalne
//2.alternatywne
//3.brak