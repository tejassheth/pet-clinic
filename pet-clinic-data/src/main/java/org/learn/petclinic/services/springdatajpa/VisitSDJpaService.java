package org.learn.petclinic.services.springdatajpa;

import lombok.AllArgsConstructor;
import org.learn.petclinic.model.Visit;
import org.learn.petclinic.repositories.VisitRepository;
import org.learn.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Profile("springdatajpa")
@AllArgsConstructor
@Service
public class VisitSDJpaService implements VisitService {
    private final VisitRepository visitRepository;
    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits= new HashSet<>();
         visitRepository.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long aLong) {
        return visitRepository.findById(aLong).orElse(null);
    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Visit visit) {
        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(Long aLong) {
        visitRepository.deleteById(aLong);
    }
}
