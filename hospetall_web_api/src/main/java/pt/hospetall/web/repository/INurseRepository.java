package pt.hospetall.web.repository;


import pt.hospetall.web.model.Nurse;

import javax.transaction.Transactional;

@Transactional
public interface INurseRepository extends IPersonBaseRepository<Nurse> { }
