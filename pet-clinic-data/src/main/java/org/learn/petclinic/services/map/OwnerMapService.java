package org.learn.petclinic.services.map;

import org.learn.petclinic.model.Owner;
import org.learn.petclinic.model.Pet;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetService;
import org.learn.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default","map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {
    private PetTypeService petTypeService;
    private PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner object) {
        if (object!=null){
            if(object.getPets() !=null){
                object.getPets().forEach(pet->{
                    if(pet.getPetType()!=null){
                        if(pet.getPetType().getId()==null){
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    }else {
                        throw new RuntimeException("Pet Type is required");
                    }
                    if(pet.getId()==null){
                        Pet savedPet = petService.save(pet);
                        pet.setId(savedPet.getId());
                    }
                });
            }
            return super.save(object);

        }else
            return null;
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
      return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().contains(lastName))
                .findFirst()
                .orElse(null);
    }
}
