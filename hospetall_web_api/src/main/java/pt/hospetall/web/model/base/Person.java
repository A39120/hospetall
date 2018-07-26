package pt.hospetall.web.model.base;

import pt.hospetall.web.model.security.Account;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person extends BaseEntity{

	private String familyName;
	private String givenName;
	private String email;
	private String telephone;

	@OneToOne
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
}
