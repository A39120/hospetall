package com.example.android_database_com_test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vet")
public class VeterinarianController {

    private final VeterinarianRepository veterinarianRepository;

    @Autowired
    VeterinarianController(VeterinarianRepository veterinarianRepository){
        this.veterinarianRepository = veterinarianRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    List<Veterinarian> getVeterinarianList(){
        return veterinarianRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    Veterinarian getVeterinarian(@PathVariable int id){
        return veterinarianRepository.getOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody String input){
        Veterinarian vet = new Veterinarian();
        vet.setName(input);
        veterinarianRepository.save(vet);

        URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }
}
