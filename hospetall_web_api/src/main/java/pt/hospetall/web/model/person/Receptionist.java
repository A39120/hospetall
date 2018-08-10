package pt.hospetall.web.model.person;

import pt.hospetall.web.model.base.Person;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Receptionist")
public class Receptionist extends Person { }
