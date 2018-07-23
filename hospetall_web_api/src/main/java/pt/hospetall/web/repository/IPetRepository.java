package pt.hospetall.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.Pet;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource(collectionResourceRel = "pets", path = "pet")
public interface IPetRepository extends PagingAndSortingRepository<Pet, Integer> {

	//Optional<Pet> findByChip_number(@Param("chip") int number);
	List<Pet> findAllByNameContaining(@Param("name") String name);
	List<Pet> findPetByBirthdateBefore(@Param("birth")long date);
	List<Pet> findPetByBirthdateAfter(@Param("birth")long date);

}
