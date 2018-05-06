package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.model.Person;

import java.util.Optional;


public interface PersonRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findPersonByTelephone(String telephone);
	Optional<Person> findPersonByEmail(String email);
	Iterable<Person> findPeopleByFamilyNameAndGivenName(String familyName, String givenName);
	Iterable<Person> findPeopleByGivenNameAndFamilyName(String familyName, String givenName);
	Iterable<Person> findPeopleByFamilyName(String name);
	Iterable<Person> findPeopleByGivenName(String name);
}
