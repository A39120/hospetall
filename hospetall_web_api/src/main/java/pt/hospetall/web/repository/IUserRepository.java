package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pt.hospetall.web.model.security.Account;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findUserByUsername(@Param("username")String username);

}
