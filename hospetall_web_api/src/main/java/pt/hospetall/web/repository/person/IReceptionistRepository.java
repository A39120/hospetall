package pt.hospetall.web.repository.person;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.person.Receptionist;
import pt.hospetall.web.repository.base.IPersonBaseRepository;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECEPTIONIST')")
@RepositoryRestResource(collectionResourceRel = "receptionists", path = "receptionist")
public interface IReceptionistRepository extends IPersonBaseRepository<Receptionist> { }
