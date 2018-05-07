package pt.hospetall.web.repository;

import pt.hospetall.web.model.Veterinarian;

import javax.transaction.Transactional;

@Transactional
public interface IVeterinarianRepository extends IPersonBaseRepository<Veterinarian> { }
