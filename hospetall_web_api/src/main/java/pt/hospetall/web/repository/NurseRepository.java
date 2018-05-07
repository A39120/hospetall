package pt.hospetall.web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.hospetall.web.model.Nurse;

import javax.transaction.Transactional;

@Transactional
public interface NurseRepository extends PersonBaseRepository<Nurse> { }
