package pt.hospetall.web.person;

import pt.hospetall.web.account.Account;

import javax.persistence.*;

@Entity
@Table(name="Person")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Person {

	@Id
	@GeneratedValue
	private int id;
	private String familyName;
	private String givenName;
	private String email;
	private String telephone;

	@OneToOne
	private Account account;

}
