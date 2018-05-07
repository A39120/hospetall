package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.hospetall.web.model.Person;

import java.util.Optional;

@NoRepositoryBean
interface IPersonBaseRepository<T extends Person> extends JpaRepository<T, Integer> {

	Optional<T> findByTelephone(String telephone);
	Optional<T> findByEmail(String email);
	Iterable<T> findByFamilyNameAndGivenName(String familyName, String givenName);
	Iterable<T> findByGivenNameAndFamilyName(String familyName, String givenName);
	Iterable<T> findByFamilyName(String name);
	Iterable<T> findByGivenName(String name);
}
