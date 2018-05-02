package pt.hospetall.web.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.person.Person;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="Account")
public class Account {

	@OneToOne
	private Person person;

	@Id
	private String username;

	@JsonIgnore
	private String password;
	private Date registerDate;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
