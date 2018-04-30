package pt.hospetall.web.race;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Integer> {
    List<Race> findRacesByNameContaining(String name);
}
