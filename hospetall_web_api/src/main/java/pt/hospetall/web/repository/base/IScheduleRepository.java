package pt.hospetall.web.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.base.Schedule;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_VETERINARIAN', 'ROLE_NURSE', 'ROLE_ADMIN')")
@NoRepositoryBean
public interface IScheduleRepository<T extends Schedule> extends PagingAndSortingRepository<T, Integer> {

	@Override
	@PostAuthorize("returnObject.get().client.email == principal.username")
	Optional<T> findById(Integer integer);


	List<T> findAllByStartDateOrderByStartDate(@Param("date") long date);

	@PreAuthorize("email == principal.username or hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_VETERINARIAN', 'ROLE_NURSE', 'ROLE_ADMIN')")
	List<T> findAllByClientEmailOrderByStartDate(@Param("email")String email);

}
