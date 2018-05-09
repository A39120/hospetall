package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.hospetall.web.model.base.MedicalProcedure;
import pt.hospetall.web.model.Pet;

import java.util.List;

@NoRepositoryBean
public interface IMedicalProcedureRepository<T extends MedicalProcedure> extends JpaRepository<T, Integer> {

	List<T> findByPet(Pet pet);
}
