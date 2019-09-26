package org.learn.petclinic.services;

import org.learn.petclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(long id);

    Vet save(Vet vet);

    Set<Vet> findAll();
}
