package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

	Iterable<Account> findAccountByUsernameContaining(String s);
	Optional<Account> findByUsername(String s);
}

