package pt.hospetall.web.person.nurse;


import pt.hospetall.web.person.PersonRepository;

import javax.transaction.Transactional;

@Transactional
public interface NurseRepository extends PersonRepository<Nurse> { }
