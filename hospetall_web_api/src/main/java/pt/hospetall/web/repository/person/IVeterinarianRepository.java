package pt.hospetall.web.repository.person;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.repository.base.IPersonBaseRepository;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIAN', 'ROLE_CLIENT')")
@RepositoryRestResource(collectionResourceRel = "veterinarian", path = "veterinarian")
public interface IVeterinarianRepository extends IPersonBaseRepository<Veterinarian> { }
