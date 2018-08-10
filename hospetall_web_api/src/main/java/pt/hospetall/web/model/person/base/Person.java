package pt.hospetall.web.model.person.base;

import pt.hospetall.web.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class Person extends BaseEntity {

	@Column(name = "family_name", nullable = false)
	private String familyName;

	@Column(name = "given_name", nullable = false)
	private String givenName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "telephone", unique = true, nullable = false)
	private String telephone;

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

}
