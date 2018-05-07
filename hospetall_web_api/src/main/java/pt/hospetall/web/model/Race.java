package pt.hospetall.web.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Race")
public class Race extends NameBaseEntity{

	@OneToMany
	private Set<Pet> pets = new HashSet<Pet>();

	@ManyToOne
	private Species species;
}
