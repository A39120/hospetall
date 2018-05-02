package pt.hospetall.web.person;

import pt.hospetall.web.account.Account;

import javax.persistence.*;

@Entity
@Table(name="Person")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

	@Id
	@GeneratedValue
	private int id;
	private String familyName;
	private String givenName;
	private String email;
	private String telephone;

	@OneToOne(mappedBy = "person")
	private Account account;

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int person_id) {
		this.id = person_id;
	}
}
