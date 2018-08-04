package pt.hospetall.web.repository.pet;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.Race;

@RepositoryRestResource(collectionResourceRel = "races", path = "race")
public interface IRaceRepository extends PagingAndSortingRepository<Race, Integer> {}
