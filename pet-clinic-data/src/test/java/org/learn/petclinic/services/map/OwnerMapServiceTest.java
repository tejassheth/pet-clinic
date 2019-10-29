package org.learn.petclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;



    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(),new PetMapService())
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByLastName() {
    }
}