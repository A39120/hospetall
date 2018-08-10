package pt.hospetall.web.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.person.base.Person;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_NURSE', 'ROLE_VETERINARIAN', 'ROLE_CLIENT')")
@NoRepositoryBean
public interface IPersonBaseRepository<T extends Person> extends PagingAndSortingRepository<T, Integer> {

	Optional<T> findByTelephone(@Param("telephone")String telephone);
	Optional<T> findByEmail(@Param("email")String email);
	List<T> findByFamilyNameAndGivenName(@Param("lastName")String familyName, @Param("firstName")String givenName);
	List<T> findByFamilyName(@Param("name")String name);
	List<T> findByGivenName(@Param("name")String name);
}
