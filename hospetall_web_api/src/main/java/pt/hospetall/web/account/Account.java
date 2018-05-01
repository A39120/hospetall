package pt.hospetall.web.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.person.Person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
public class Account {

	@OneToOne
	private Person person;

	@GeneratedValue
	@Id
	private int id;
	private String username;

	@JsonIgnore
	private String password;
	private Date registerDate;

}
