package pt.hospetall.web.model.security;

import pt.hospetall.web.model.base.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="account")
public class Account extends BaseEntity {

	@Column(name="username", unique = true, nullable = false)
	private String username;

	@Column(name="password", nullable = false, length = 63)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="account_authorities",
			joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name="authority_id", referencedColumnName = "id")})
	private Set<Authority> authorities;

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

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

}
