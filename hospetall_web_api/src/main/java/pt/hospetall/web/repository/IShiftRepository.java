package pt.hospetall.web.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import pt.hospetall.web.model.shift.Shift;

@NoRepositoryBean
public interface IShiftRepository<T extends Shift> extends PagingAndSortingRepository<T, Integer> {
}
