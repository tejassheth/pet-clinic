package org.learn.petclinic.repositories;

import org.learn.petclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository extends CrudRepository<Speciality,Long> {
}
