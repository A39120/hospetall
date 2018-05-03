package pt.hospetall.web.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PersonRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findPersonByTelephone(String telephone);
	Optional<Person> findPersonByEmail(String email);
	Iterable<Person> findPeopleByFamilyNameAndGivenName(String familyName, String givenName);
	Iterable<Person> findPeopleByGivenNameAndFamilyName(String familyName, String givenName);
	Iterable<Person> findPeopleByFamilyName(String name);
	Iterable<Person> findPeopleByGivenName(String name);
}
