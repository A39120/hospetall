package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.Person;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client extends Person {

	@NotBlank
	private String address;
	@NotBlank
	private String postalCode;
	private String telephoneAlternative;

	private String other;

	@JsonIgnore
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
}
