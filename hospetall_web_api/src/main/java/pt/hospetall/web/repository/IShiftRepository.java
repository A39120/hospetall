package pt.hospetall.web.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import pt.hospetall.web.model.shift.Shift;

import java.util.List;

@NoRepositoryBean
public interface IShiftRepository<T extends Shift> extends PagingAndSortingRepository<T, Integer> {

	List<T> findAllByStartPeriodAfterOrWeeklyIsTrue(long after);
	List<T> findAll();


}
