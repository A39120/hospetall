package pt.hospetall.web.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

	Iterable<Account> findAccountByUsernameContaining(String s);
	Optional<Account> findByUsername(String s);
}

