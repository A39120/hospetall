package pt.hospetall.web.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.Client;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "clients", path="client")
public interface IClientRepository extends IPersonBaseRepository<Client> {

	@Override
	@PostAuthorize("returnObject.get().email == principal.username or hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
	Optional<Client> findById(@Param("id") Integer id);

	Optional<Client> findClientByAddress(@Param("address") String address);
	Optional<Client> findClientByPostalCode(@Param("postal") String postalCode);
	Optional<Client> findClientByTelephone(@Param("telephone") String telephone);
	Optional<Client> findClientByNif(@Param("nif") int nif);
}




