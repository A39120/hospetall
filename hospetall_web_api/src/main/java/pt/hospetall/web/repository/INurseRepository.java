package pt.hospetall.web.repository;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.Nurse;

@RepositoryRestResource(collectionResourceRel = "nurses", path = "nurse")
public interface INurseRepository extends IPersonBaseRepository<Nurse> { }
