package pt.hospetall.web.person;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.account.Account;

import java.util.Optional;


public interface PersonRepository<T extends Person> extends JpaRepository<T, Integer> {

	Optional<Person> findPersonByAccount(Account account);
	Optional<Person> findPersonByTelephone(String telephone);
	Optional<Person> findPersonByEmail(String email);
	Iterable<Person> findPeopleByFamilyNameAndGivenName(String familyName, String givenName);
	Iterable<Person> findPeopleByGivenNameAndFamilyName(String familyName, String givenName);
	Iterable<Person> findPeopleByFamilyName(String name);
	Iterable<Person> findPeopleByGivenName(String name);
}
