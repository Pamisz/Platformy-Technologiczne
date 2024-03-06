package org.example;
import java.util.Comparator;

public class alternatywka implements Comparator<Mage>{
	@Override
	public int compare(Mage o1, Mage o2) {
		int comparison = Double.compare(o1.get_power(), o2.get_power());
		if(comparison  != 0){						//porównanie mocy
			return  comparison;
		}

		//moc taka sama
		comparison = Integer.compare(o1.get_level(), o2.get_level());
		if (comparison != 0){
			return comparison;
		}

		//taka sama moc i level
		return comparison = o1.get_name().compareTo(o2.get_name());
	}
}
