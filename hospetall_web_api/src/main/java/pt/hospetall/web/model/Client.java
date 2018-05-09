package pt.hospetall.web.model;

import pt.hospetall.web.model.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client extends Person {

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

	public void setOther(String other) {
		this.other = other;
	}
}
