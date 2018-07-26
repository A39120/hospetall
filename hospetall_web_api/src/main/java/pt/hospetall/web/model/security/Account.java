package pt.hospetall.web.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.model.security.Authority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Account")
public class Account extends BaseEntity {

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	private long registerDate;

	@ManyToMany
	private Set<Authority> authorities = new HashSet<>();

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
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

	public long getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(long registerDate) {
		this.registerDate = registerDate;
	}
}


