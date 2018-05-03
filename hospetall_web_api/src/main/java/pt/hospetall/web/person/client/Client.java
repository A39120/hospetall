package pt.hospetall.web.person.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.person.Person;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue
	private int id;

	@JsonIgnore
	@OneToOne
	private Person person;
	private String address;
	private String postalCode;
	private String telephoneAlternative;
	private int nif;
	private String other;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
