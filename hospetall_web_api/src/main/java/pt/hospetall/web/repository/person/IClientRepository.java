package pt.hospetall.web.repository.person;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.repository.base.IPersonBaseRepository;

import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "clients", path="client")
public interface IClientRepository extends IPersonBaseRepository<Client> {

	@PreAuthorize("#email == principal.username or hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	Optional<Client> findByEmail(@P("email") String email);

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_CLIENT')")
	@PostAuthorize("returnObject.get().email == principal.username or hasAnyRole('ROLE_ADMIN','ROLE_RECEPTIONIST')")
	Optional<Client> findById(@Param(value="id") Integer id);

	Optional<Client> findClientByAddress(@Param("address") String address);
	Optional<Client> findClientByPostalCode(@Param("postal") String postalCode);
	Optional<Client> findClientByTelephone(@Param("telephone") String telephone);
	Optional<Client> findClientByNif(@Param("nif") int nif);

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	@Override
	<S extends Client> S save(S entity);

	@Override
	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	<S extends Client> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	boolean existsById(Integer integer);

	@Override
	long count();

	@Override
	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	void deleteById(Integer integer);

	@Override
	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	void delete(Client entity);
}




