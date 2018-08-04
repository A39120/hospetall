package pt.hospetall.web.repository.person;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.repository.base.IPersonBaseRepository;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_NURSE', 'ROLE_RECEPTIONIST', 'ROLE_CLIENT')")
@RepositoryRestResource(collectionResourceRel = "nurses", path = "nurse")
public interface INurseRepository extends IPersonBaseRepository<Nurse> { }
