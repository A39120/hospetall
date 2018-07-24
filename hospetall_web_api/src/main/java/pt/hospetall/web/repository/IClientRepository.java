package pt.hospetall.web.repository;

import pt.hospetall.web.model.Client;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface IClientRepository extends IPersonBaseRepository<Client> {

	 Optional<Client> findClientByAddress(String address);
	 Optional<Client> findClientByPostalCode(String postalCode);
	 Optional<Client> findClientByTelephoneAlternative(String alterTelephone);
	 Optional<Client> findClientByNif(String nif);
}
