package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.model.Veterinarian;

import javax.transaction.Transactional;

@Transactional
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer> { }
