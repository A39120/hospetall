package pt.hospetall.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pt.hospetall.web.model.base.MedicalProcedure;
import pt.hospetall.web.model.Pet;

import java.util.List;

@NoRepositoryBean
public interface IMedicalProcedureRepository<T extends MedicalProcedure> extends PagingAndSortingRepository<T, Integer> {

	List<T> findAllByPetId(@Param("pet")int pet);
	List<T> findAllByPetName(@Param("pet")String pet);

	List<T> findAllByCaseHistoryContains(@Param("case") String history);

	List<T> findAllByDiagnosisContains(@Param("diagnosis")String diagnosis);
	List<T> findAllByTreatmentContains(@Param("treatment")String treatment);
}
