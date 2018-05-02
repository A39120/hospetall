package pt.hospetall.web.person.client;

import pt.hospetall.web.person.Person;
import pt.hospetall.web.pet.Pet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Client")
@PrimaryKeyJoinColumn(name = "person_id")
public class Client extends Person {

	@Id
	@GeneratedValue
	private int id;
	private String address;
	private String postalCode;
	private String telephoneAlternative;
	private int nif;
	private String other;

	@OneToMany(mappedBy = "owner")
	private Set<Pet> pets = new HashSet<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTelephoneAlternative() {
		return telephoneAlternative;
	}

	public void setTelephoneAlternative(String telephoneAlternative) {
		this.telephoneAlternative = telephoneAlternative;
	}

	public int getNif() {
		return nif;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
