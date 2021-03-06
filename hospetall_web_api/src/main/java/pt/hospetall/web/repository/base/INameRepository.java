package pt.hospetall.web.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.hospetall.web.model.base.NameBaseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface INameRepository<T extends NameBaseEntity> extends JpaRepository<T, Integer> {

	List<T> findByNameContaining(String name);
	Optional<T> findByName(String name);
}
