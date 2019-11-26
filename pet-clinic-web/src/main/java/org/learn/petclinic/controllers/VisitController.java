package org.learn.petclinic.controllers;

import org.learn.petclinic.model.Pet;
import org.learn.petclinic.model.Visit;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetService;
import org.learn.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits/")
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, OwnerService ownerService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }
     @ModelAttribute("visit")
    private Visit loadPetWithVisit(@PathVariable Long petId, Map<String,Object> model){
         Pet pet = petService.findById(petId);
         model.put("pet",pet);
         Visit visit = Visit.builder().build();
         pet.getVisits().add(visit);
         visit.setPet(pet);
        return visit;
    }

    @GetMapping("/new")
    public String initCreateVisitForm(@PathVariable Long petId, Map<String,Object> model){
        return "pets/createOrUpdateVisitForm";
    }

    @InitBinder("pet")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    @PostMapping("/new")
    public String processCreateVisitForm(Pet pet, @Valid Visit visit, BindingResult result, Model model){
        if(result.hasErrors()){
            return "pets/createOrUpdateVisitForm";
        }else{
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
