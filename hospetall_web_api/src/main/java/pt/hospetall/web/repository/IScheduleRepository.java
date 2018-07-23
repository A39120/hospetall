package pt.hospetall.web.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.base.Schedule;

import java.util.List;


@NoRepositoryBean
public interface IScheduleRepository<T extends Schedule> extends PagingAndSortingRepository<T, Integer> {

	List<T> findAllByStartDateOrderByStartDate(@Param("date") long date);

	/*
	List<Schedule> findAllByClientIdAndStartDateAfterOrderByStartDate(@PathVariable int clientId, long after);
	List<Schedule> findAllByStartDateAfterOrderByStartDate(long current);
	List<Schedule> findAllByStartDateBetween(long start, long finish);
	Optional<Schedule> findByStartDateEqualsAndPeriodEqualsAndClientEquals(long start, long period, Client client);
	List<Schedule> findAllByVeterinarianIdAndStartDateAfterOrderByStartDate(int id, long after);
	List<Schedule> findAllByNurseIdAndStartDateAfterOrderByStartDate(int id, long after);
	List<Schedule> findAllByVeterinarianNotNullAndStartDateAfterOrderByStartDate(long after);
	List<Schedule> findAllByNurseNotNullAndStartDateAfterOrderByStartDate(long after);
	*/

}
