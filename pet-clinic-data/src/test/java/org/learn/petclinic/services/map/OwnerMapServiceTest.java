package org.learn.petclinic.services.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learn.petclinic.model.Owner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    private final Long ownerId=1L;
    private final String lastName = "Tejas";
    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(),new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1,owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId,owner.getId());
    }

    @Test
    void saveExistingId() {
        long id = 2L;
        Owner owner = Owner.builder().id(id).build();
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id,owner.getId());
    }

    @Test
    void saveNoId() {
        Owner owner = Owner.builder().build();
        Owner savedOwner = ownerMapService.save(owner);
        Assertions.assertNotNull(savedOwner);
        Assertions.assertNotNull(savedOwner.getId());


    }
    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner tejas = ownerMapService.findByLastName(lastName);
        assertNotNull(tejas);
        assertEquals(ownerId,tejas.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner tejas = ownerMapService.findByLastName("lastName");
        assertNull(tejas);
    }

}