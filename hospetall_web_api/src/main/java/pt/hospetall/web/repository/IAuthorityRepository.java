package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.hospetall.web.model.security.Authority;

import java.util.Optional;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {

	Optional<Authority> findByAuthority(@Param("authority")String authority);

}
