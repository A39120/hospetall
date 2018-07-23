package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="Account")
public class Account {

	//@OneToOne
//	private Person person;

	@Id
	private String username;

	@JsonIgnore
	private String password;

	private long registerDate;
	private short roles; // 0x0000 - 0xFFFF

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

	public long getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(long registerDate) {
		this.registerDate = registerDate;
	}

	public short getRoles() {
		return roles;
	}

	public void setRoles(short roles) {
		this.roles = roles;
	}
}
