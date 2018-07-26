package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pt.hospetall.web.model.security.Account;

import java.util.Optional;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findByUsername(@Param("username") String username);
}

