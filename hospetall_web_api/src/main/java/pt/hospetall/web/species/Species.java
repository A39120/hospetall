package pt.hospetall.web.species;

import pt.hospetall.web.pet.Pet;
import pt.hospetall.web.race.Race;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Species {

	@Id
	@GeneratedValue
	private int id;
	private String name;

	@OneToMany(mappedBy = "species")
	private Set<Pet> pets = new HashSet<>();

	@OneToMany(mappedBy = "species")
	private Set<Race> races = new HashSet<>();

}

