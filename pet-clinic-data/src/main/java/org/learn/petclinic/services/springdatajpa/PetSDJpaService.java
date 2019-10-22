package org.learn.petclinic.services.springdatajpa;

import lombok.AllArgsConstructor;
import org.learn.petclinic.model.Pet;
import org.learn.petclinic.repositories.PetRepository;
import org.learn.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {
    private final PetRepository petTypeRepository;

    @Override
    public Set<Pet> findAll() {
        Set<Pet> petSet = new HashSet<>();
        petTypeRepository.findAll().forEach(petSet::add);
        return petSet;
    }

    @Override
    public Pet findById(Long aLong) {
        return petTypeRepository.findById(aLong).orElse(null);
    }

    @Override
    public Pet save(Pet object) {
        return petTypeRepository.save(object);
    }

    @Override
    public void delete(Pet object) {
        petTypeRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        petTypeRepository.deleteById(aLong);
    }
}
