package pt.hospetall.web.client;

import pt.hospetall.web.person.Person;
import pt.hospetall.web.pet.Pet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client extends Person{

	private String address;
	private String postalCode;
	private String telephoneAlternative;
	private int nif;
	private String other;

	@OneToMany(mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

	public int getNif(){ return nif; }
	public String getAddress(){return address; }
	public String getPostalCode(){ return postalCode; }
	public String getTelephoneAlternative(){ return telephoneAlternative; }
	public String getOther(){ return other; }

	public void setNif(int nif){ this.nif = nif; }
	public void setAddress(String adress){ this.address = address; }
	public void setPostalCode(String postalCode){ this.postalCode = postalCode; } 
	public void setTelephoneAlternative(String telephoneAlternative){this.telephoneAlternative = telephoneAlternative; }
	public void setOther(String other){ this.other = other; }

}
