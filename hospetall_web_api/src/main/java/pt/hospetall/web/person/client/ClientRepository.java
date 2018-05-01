package pt.hospetall.web.person.client;

import pt.hospetall.web.person.PersonRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ClientRepository extends PersonRepository<Client> {

	 Optional<Client> findPersonByAddress(String address);
	 Optional<Client> findPersonByPostalCode(String postalCode);
	 Optional<Client> findPersonByTelephoneAlternative(String alterTelephone);
	 Optional<Client> findPersonByNif(int nif);
}
