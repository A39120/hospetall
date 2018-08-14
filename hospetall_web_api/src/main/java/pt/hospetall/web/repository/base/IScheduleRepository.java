package pt.hospetall.web.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.schedule.Schedule;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_VETERINARIAN', 'ROLE_NURSE', 'ROLE_ADMIN')")
@NoRepositoryBean
public interface IScheduleRepository<T extends Schedule> extends PagingAndSortingRepository<T, Integer> {

	@Override
	@PostAuthorize("returnObject.get().client.email == principal.username or hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_VETERINARIAN', 'ROLE_NURSE', 'ROLE_ADMIN')")
	Optional<T> findById(Integer integer);

}
