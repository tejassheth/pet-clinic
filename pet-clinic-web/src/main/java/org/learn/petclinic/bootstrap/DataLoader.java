package org.learn.petclinic.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.petclinic.model.*;
import org.learn.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    @Override
    public void run(String... args) throws Exception {
        int count=petTypeService.findAll().size();
        if(count==0){
            loadData();
        }

    }

    private void loadData() {
        PetType dog= new PetType();
        dog.setName("Dog");
        PetType savedDogType= petTypeService.save(dog);

        PetType cat= new PetType();
        cat.setName("Cat");
        PetType savedCatType= petTypeService.save(cat);

        Speciality radiology= new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology=specialtyService.save(radiology);

        Speciality surgery= new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialtyService.save(surgery);

        Speciality dentistry= new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry=specialtyService.save(dentistry);

        Owner owner1= new Owner();
        owner1.setFirstName("Tejas");
        owner1.setLastName("Sheth");
        owner1.setAddress("Surel Apartments");
        owner1.setCity("Ahmedabad");
        owner1.setTelephone("09033332295");

        Pet motiDog= new Pet();
        motiDog.setPetType(savedDogType);
        motiDog.setOwner(owner1);
        motiDog.setBirthDate(LocalDate.now());
        motiDog.setName("Moti");

        owner1.getPets().add(motiDog);
        ownerService.save(owner1);


        Visit dogVisit= new Visit();
        dogVisit.setPet(motiDog);
        dogVisit.setDate(LocalDate.now());
        dogVisit.setDescription("Sneezy Kitty");

        visitService.save(dogVisit);

        Owner owner2 = new Owner();
        owner2.setFirstName("Radhika");
        owner2.setLastName("Sheth");
        owner2.setAddress("Bahelapara , Kamdar Street");
        owner2.setCity("Limbdi");
        owner2.setTelephone("09510094794");

        Pet tituCat= new Pet();
        tituCat.setPetType(savedCatType);
        tituCat.setOwner(owner1);
        tituCat.setBirthDate(LocalDate.now());
        tituCat.setName("Titu");

        owner2.getPets().add(tituCat);

        ownerService.save(owner2);

        Visit catVisit= new Visit();
        catVisit.setPet(tituCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);
        log.info("Loaded Owners.....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Dhyani");
        vet1.setLastName("Sheth");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jiya");
        vet2.setLastName("Sheth");
        vet2.getSpecialities().add(savedSurgery);
        vetService.save(vet2);

        log.info("Loaded Vets.....");
    }
}
