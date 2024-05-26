package org.example;
import java.util.*;

public class Mage implements Comparable<Mage>{
	private String name;
	private int level;
	private double power;
	private Set<Mage> apprentices;

	public  Mage(String n, int l, double p, String sort){
		this.name = n;
		this.level = l;
		this.power = p;
		this.apprentices = switch (sort) {
			case "brak" -> new HashSet<>();
			case "naturalne" -> new TreeSet<>();
			case "alternatywne" -> new TreeSet<>(new alternatywka());
			default ->null;
		};
	}

	public int get_app_number(){
		int buff = 0;
		for (Mage tmp : this.apprentices){
			buff++;
			buff += tmp.get_app_number();
		}
		return buff;
	}
	public void add_apprentices(Mage m){
		this.apprentices.add(m);
	}
	public Set<Mage> get_apprentices(){
		return this.apprentices;
	}
	public String get_name(){
		return this.name;
	}
	public int get_level(){
		return this.level;
	}
	public double get_power(){
		return this.power;
	}

	void show_apprenticies(Integer Depth){
		for (int i = 0; i<Depth; i++){
			System.out.print("-");
		}
		System.out.print(this.toString() + "\n");
		for (Mage tmp : this.apprentices){
			tmp.show_apprenticies(Depth+1);
		}
	}

	public Map<Mage,Integer> maping(String sort){
		Map<Mage,Integer> tmp = switch (sort) {
			case "brak" -> new HashMap<Mage,Integer>();
			case "naturalne" -> new TreeMap<Mage,Integer>();
			case "alternatywne" -> new TreeMap<Mage,Integer>(new alternatywka());
			default ->null;
		};
		this.add_to_map(tmp);
		return tmp;
	}
	void add_to_map (Map<Mage,Integer> m){
		m.put(this, this.get_app_number());
		for (Mage tmp : this.apprentices){
			tmp.add_to_map(m);
		}
	}

	@Override
	public int compareTo(Mage o) {
		int comparison = this.name.compareTo(o.get_name());
		if (comparison != 0) {
			return comparison;
		}
		else{
			if(this.level > o.get_level()){
				return   1;
			}
			else if (this.level < o.get_level()) {
				return -1;
			}
			else{
				return Double.compare(this.power, o.get_power());
			}
		}
		//return >0 => this > o
		//return <0 => this < 0
		//return 0 => this == 0
	}

	@Override
	public String toString() {
		return "Mage{" +
				"name='" + this.name  + "'"+
				",  level='" + this.level + "'" +
				",  power='" + this.power + "'" +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Mage tmp = (Mage) o;

		return tmp.name.compareTo(this.name) == 0 && this.power == tmp.get_power() && this.level == tmp.get_level() && Objects.equals(this.apprentices, tmp.apprentices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.level, this.power, this.apprentices);
	}
}
