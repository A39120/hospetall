package pt.hospetall.web.repository;

import pt.hospetall.web.model.Pet;

import java.sql.Date;
import java.util.List;

public interface IPetRepository extends INameRepository<Pet>{

	List<Pet> findPetByBirthdateBefore(Date date);

}
