package pt.hospetall.web.client;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
	List<Client> findByGivenName(String givenName);
	List<Client> findByAddress(String address);
	List<Client> findByFamilyName(String familyName);
	List<Client> findByTelephone(String telephone);
	List<Client> findByEmail(String email);
}
