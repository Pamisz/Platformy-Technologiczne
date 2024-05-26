package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class repository {
	private final Collection<mage> mages = new ArrayList<>();

	public Collection<mage> getMages() {
		return this.mages;
	}

	public Optional<mage> find(String name) {
		for (mage m : mages) {
			if (m.getName().equals(name)) {
				return Optional.of(m);
			}
		}
		return Optional.empty();
	}

	public void delete(String name) {
		Optional<mage> m = find(name);
		if (m.isPresent()) {
			mages.remove(m.get());
		} else {
			throw new IllegalArgumentException("Mage not found: " + name);
		}
	}

	public void save(mage m) {
		if (mages.contains(m)) {
			throw new IllegalArgumentException("Mage already exists");
		}
		mages.add(m);
	}
}
