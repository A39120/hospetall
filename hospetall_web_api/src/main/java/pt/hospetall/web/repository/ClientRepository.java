package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.model.Client;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ClientRepository extends PersonBaseRepository<Client> {

	 Optional<Client> findPersonByAddress(String address);
	 Optional<Client> findPersonByPostalCode(String postalCode);
	 Optional<Client> findPersonByTelephoneAlternative(String alterTelephone);
	 Optional<Client> findPersonByNif(int nif);
}
