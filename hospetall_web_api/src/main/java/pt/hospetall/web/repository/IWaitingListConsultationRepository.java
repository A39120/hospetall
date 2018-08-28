package pt.hospetall.web.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.WaitingListConsultation;
import pt.hospetall.web.model.pet.Pet;

import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "waiting_list_consultations", path="waiting_list_consultation")
public interface IWaitingListConsultationRepository<T extends WaitingListConsultation> extends PagingAndSortingRepository<T, Integer> {

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
    Optional<Pet> findByPet(Pet pet);

}
