package pt.hospetall.web.client;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	List<Client> findByGivenName(String givenName);
	List<Client> findByAddress(String address);
	List<Client> findByFamilyName(String familyName);
	List<Client> findByTelephone(String telephone);
	List<Client> findByEmail(String email);
}
