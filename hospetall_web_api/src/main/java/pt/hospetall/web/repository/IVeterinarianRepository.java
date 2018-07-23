package pt.hospetall.web.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.Veterinarian;

import javax.transaction.Transactional;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIAN')")
@RepositoryRestResource(collectionResourceRel = "veterinarian", path = "veterinarian")
public interface IVeterinarianRepository extends IPersonBaseRepository<Veterinarian> { }
