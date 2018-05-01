package pt.hospetall.web.person.veterinarian;

import pt.hospetall.web.person.PersonRepository;

import javax.transaction.Transactional;

@Transactional
public interface VeterinarianRepository extends PersonRepository<Veterinarian> { }
