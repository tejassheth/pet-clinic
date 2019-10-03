package org.learn.petclinic.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.learn.petclinic.model.Owner;
import org.learn.petclinic.model.Vet;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1= new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Tejas");
        owner1.setLastName("Sheth");

        ownerService.save(owner1);


        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Radhika");
        owner2.setLastName("Sheth");

        ownerService.save(owner2);
        log.info("Loaded Owners.....");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Dhyani");
        vet1.setLastName("Sheth");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Jiya");
        vet2.setLastName("Sheth");

        vetService.save(vet2);

        log.info("Loaded Vets.....");

    }
}
