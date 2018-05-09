package pt.hospetall.web.model;

import pt.hospetall.web.model.base.NameBaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Species extends NameBaseEntity {

	@OneToMany(mappedBy = "species")
	private Set<Pet> pets = new HashSet<>();

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

}

