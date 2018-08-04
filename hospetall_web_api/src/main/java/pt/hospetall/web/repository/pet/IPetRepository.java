package pt.hospetall.web.repository.pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import pt.hospetall.web.model.Pet;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "pets", path = "pet")
public interface IPetRepository extends PagingAndSortingRepository<Pet, Integer> {
	//Optional<Pet> findByChip_number(@Param("chip") int number);
	List<Pet> findAllByNameContaining(@Param("name") String name);
	List<Pet> findPetByBirthdateBefore(@Param("birth")long date);
	List<Pet> findPetByBirthdateAfter(@Param("birth")long date);


	@Override
	//@PostFilter("filterObject.getOwner().getEmail() == principal.username")
	//@PostFilter("filterObject.owner.email == principal.username")
	@PostFilter("filterObject.owner.email.equals(principal.username) or hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
	Iterable<Pet> findAll(Sort sort);

	@Override
	@PostAuthorize("returnObject.get().getOwner().getEmail() == principal.username or hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
	Optional<Pet> findById(Integer integer);

	@Override
	//@PostFilter("filterObject.owner.email == principal.username")
	@PostFilter("filterObject.owner.email.equals(principal.username) or hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
	Iterable<Pet> findAll();
}
