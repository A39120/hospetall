package pt.hospetall.web.race;

import pt.hospetall.web.pet.Pet;
import pt.hospetall.web.species.Species;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Race")
public class Race {

	@Id
	private int id;
	private String name;

	@OneToMany
	private Set<Pet> pets = new HashSet<Pet>();

	@ManyToOne
	private Species species;
}
