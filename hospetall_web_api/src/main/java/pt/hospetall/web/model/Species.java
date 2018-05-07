package pt.hospetall.web.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Species extends NameBaseEntity {

	@OneToMany(mappedBy = "species")
	private Set<Pet> pets = new HashSet<>();

	@OneToMany(mappedBy = "species")
	private Set<Race> races = new HashSet<>();

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	public Set<Race> getRaces() {
		return races;
	}

	public void setRaces(Set<Race> races) {
		this.races = races;
	}
}

