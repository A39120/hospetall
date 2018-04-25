package pt.hospetall.web.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String givenName;
	private String familyName;
	private String address;
	private String postalCode;
	private String telephone;
	private String telephoneAlternative;
	private String email;
	private int nif;
	private String other;

	public int getId(){ return id; }
	public int getNif(){ return nif; }
	public String getGivenName(){ return givenName; }
	public String getFamilyName(){ return familyName; }
	public String getAddress(){return adress; }
	public String getPostalCode(){ return postalCode; }
	public String getTelephone(){ return telephone; }
	public String getTelephoneAlternative(){ return telephoneAlternative; }
	public String getEmail(){return email; }
	public String getOther(){ return other; }

	public void setId(int id){ this.id = id; } 
	public void setNif(int nif){ this.nif = nif; } 
	public void setGivenName(String givenName){ this.givenName = givenName; }
	public void setFamilyName(String familyName){ this.familyName = familyName; } 
	public void setAddress(String adress){ this.address = address; } 
	public void setPostalCode(String postalCode){ this.postalCode = postalCode; } 
	public void setTelephone(String telephone){ this.telephone = telephone; } 
	public void setTelephoneAlternative(String telephoneAlternative){this.telephoneAlternative = telephoneAlternative; } 
	public void setEmail(String email){ this.email = email; } 
	public void setOther(String other){ this.other = other; }

}
