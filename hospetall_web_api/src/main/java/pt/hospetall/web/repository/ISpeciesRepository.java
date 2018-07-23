package pt.hospetall.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.Species;

@RepositoryRestResource(collectionResourceRel = "species", path = "species")
public interface ISpeciesRepository extends PagingAndSortingRepository<Species, Integer>{}
