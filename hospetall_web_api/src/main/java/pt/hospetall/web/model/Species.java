package pt.hospetall.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Species extends NameBaseEntity{

	@OneToMany(mappedBy = "species")
	private Set<Pet> pets = new HashSet<>();

	@OneToMany(mappedBy = "species")
	private Set<Race> races = new HashSet<>();
}

