package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.security.Account;

import java.util.Optional;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RepositoryRestResource(exported = false)
public interface IUserRepository extends JpaRepository<Account, Integer> {
	Optional<Account> findUserByUsername(@Param("username")String username);
}
