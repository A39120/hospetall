package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.base.Person;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IPersonBaseRepository<T extends Person> extends PagingAndSortingRepository<T, Integer> {

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<T> findByTelephone(@Param("telephone")String telephone);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<T> findByEmail(@Param("email")String email);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	List<T> findByFamilyNameAndGivenName(@Param("lastName")String familyName, @Param("firstName")String givenName);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	List<T> findByFamilyName(@Param("name")String name);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	List<T> findByGivenName(@Param("name")String name);
}
