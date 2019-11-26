package org.learn.petclinic.controllers;

import org.learn.petclinic.model.Owner;
import org.learn.petclinic.model.Pet;
import org.learn.petclinic.model.PetType;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetService;
import org.learn.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }
    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initPetCreationForm(Owner owner, Model model ){
        Pet pet =Pet.builder().build();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("owner",owner);
        model.addAttribute("pet",pet);
        return  "pets/createOrUpdatePetForm";
    }

    @PostMapping("/new")
    public String processPetCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model){
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            pet.setOwner(owner);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


    @GetMapping("/{petId}/edit")
    public String initPetUpdateForm(@PathVariable Long petId, Model model){
            model.addAttribute("pet",petService.findById(petId));
            return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/{petId}/edit")
    public String processPetUpdateForm(Owner owner, @Valid Pet pet, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            pet.setOwner(owner);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
