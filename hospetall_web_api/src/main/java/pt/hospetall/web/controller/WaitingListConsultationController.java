package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.error.exceptions.PetNotFoundException;
import pt.hospetall.web.error.exceptions.WaitingListNotFoundException;
import pt.hospetall.web.model.WaitingListConsultation;

import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.repository.IWaitingListConsultationRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.pet.IPetRepository;
import pt.hospetall.web.services.WaitingListService;

import java.util.List;
import java.util.Optional;

@BasePathAwareController
//@RepositoryRestController
public class WaitingListConsultationController {

    private final WaitingListService waitingListService;
    private final IPetRepository petRepository;
    private final IVeterinarianRepository veterinarianRepository;

    @Autowired
    public WaitingListConsultationController(WaitingListService waitingListService, IPetRepository petRepository, IWaitingListConsultationRepository waitingListConsultationRepository, IVeterinarianRepository veterinarianRepository ){
        this.waitingListService = waitingListService;
        this.petRepository = petRepository;
        this.veterinarianRepository = veterinarianRepository;
     }


    @PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
    @PostMapping(path = "/pet/{id}/waitinglist/consultation")
    public ResponseEntity insertPetInWaitingList(
            @PathVariable(name="id", required = true) int pet_id,
            @RequestBody WaitingListConsultation wlc) throws PetNotFoundException {

        Pet pet = petRepository.findById(pet_id).orElseThrow(PetNotFoundException::new);
        wlc.setPet(pet);
        waitingListService.addToWLConsultation(wlc);
        return ResponseEntity.ok().build();
    }



    @PreAuthorize("hasAnyRole('ROLE_VETERINARIAN', 'ROLE_ADMIN')")
    @PostMapping(path = "/waitinglist/consultation/callNext")
    public ResponseEntity callNextPetInWLC(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Veterinarian vet = veterinarianRepository.findByEmail(principal.getUsername()).orElseThrow(PersonNotFoundException::new);

        WaitingListConsultation wlc = waitingListService.pollFromWLConsultation(vet.getId());
        return ResponseEntity.ok(wlc);
    }


/*
    @PreAuthorize("hasAnyRole('ROLE_VETERINARIAN', 'ROLE_ADMIN')")
    @PutMapping(path = "/WaitingListConsultation/{id}")
    public ResponseEntity changePatientPriority(
            @PathVariable(name="id", required = true) int wlc_id,
            @RequestBody WaitingListConsultation wlc
    ) {

        wlc = waitingListService.changeElemWLConsultation(wlc);
        return ResponseEntity.ok(wlc);
    }*/


    @PreAuthorize("hasAnyRole('ROLE_VETERINARIAN', 'ROLE_ADMIN')")
    @GetMapping(path = "/waitinglist/consultation/peekNext")
    public ResponseEntity peekNextPetInWLC(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Veterinarian vet = veterinarianRepository.findByEmail(principal.getUsername()).orElseThrow(PersonNotFoundException::new);

        WaitingListConsultation wlc = waitingListService.peekFromWLConsultation(vet.getId());
        return ResponseEntity.ok(wlc);
    }


    @PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
    @GetMapping(path = "/waitinglist/consultation/sorted")
    public ResponseEntity<List<WaitingListConsultation>> getSortedWLC() {
        List<WaitingListConsultation> wlc = waitingListService.getSorted();
       // return ResponseEntity.ok();
        return new ResponseEntity<>(wlc, HttpStatus.OK);
    }



}
