package org.example;

import java.text.ParseException;
import java.util.Optional;

public class controller {
	private repository MageRepository;
	public controller(repository MageRepository) {
		this.MageRepository = MageRepository;
	}
	public String find(String name) {
		Optional<mage> m = MageRepository.find(name);
		if(m.isPresent()){
			return m.toString();
		}else{
			return "not found";
		}
	}
	public String delete(String name) {
		Optional<mage> m = MageRepository.find(name);
		String message;
		if(m.isPresent()){
			message = "done";
		}else{
			message ="not found";
		}
		MageRepository.delete(name);
		return message;
	}
	public String save(String name, String level) {
		Optional<mage> m = MageRepository.find(name);
		String message;
		if (m.isPresent()) {
			message = "bad request";
		} else {
			message = "done";
		}
		int lev = 0;
		if (level != null && !level.isEmpty()) {
			try {
				lev = Integer.parseInt(level);
			} catch (NumberFormatException e) {
				System.err.println("Nieprawidłowy format liczby dla level: " + level);
			}
		}
		mage mag = new mage(name, lev);
		MageRepository.save(mag);
		return message;
	}

}
