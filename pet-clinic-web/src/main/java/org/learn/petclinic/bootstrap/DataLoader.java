package org.learn.petclinic.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.learn.petclinic.model.Owner;
import org.learn.petclinic.model.PetType;
import org.learn.petclinic.model.Vet;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetTypeService;
import org.learn.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog= new PetType();
        dog.setName("Dog");
        PetType savedDogType= petTypeService.save(dog);

        PetType cat= new PetType();
        cat.setName("Cat");
        PetType savedCatType= petTypeService.save(cat);

        Owner owner1= new Owner();
        owner1.setFirstName("Tejas");
        owner1.setLastName("Sheth");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Radhika");
        owner2.setLastName("Sheth");

        ownerService.save(owner2);
        log.info("Loaded Owners.....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Dhyani");
        vet1.setLastName("Sheth");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jiya");
        vet2.setLastName("Sheth");

        vetService.save(vet2);

        log.info("Loaded Vets.....");

    }
}
