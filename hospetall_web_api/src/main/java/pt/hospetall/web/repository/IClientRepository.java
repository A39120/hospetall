package pt.hospetall.web.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.Client;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_CLIENT')")
@RepositoryRestResource(collectionResourceRel = "clients", path="client")
public interface IClientRepository extends IPersonBaseRepository<Client> {

	@Override
	@PostAuthorize("returnObject.get().account.username == principal.username or hasRole('ROLE_WORKER')")
	Optional<Client> findById(@Param("id") Integer id);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<Client> findClientByAddress(@Param("address") String address);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<Client> findClientByPostalCode(@Param("postal") String postalCode);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<Client> findClientByTelephone(@Param("telephone") String telephone);

	@PreAuthorize("hasRole('ROLE_WORKER')")
	Optional<Client> findClientByNif(@Param("nif") int nif);
}




