package pt.hospetall.web.person.client;

import pt.hospetall.web.person.Person;
import pt.hospetall.web.pet.Pet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client extends Person {

	private String address;
	private String postalCode;
	private String telephoneAlternative;
	private int nif;
	private String other;

	@OneToMany(mappedBy = "owner")
	private Set<Pet> pets = new HashSet<>();

}
