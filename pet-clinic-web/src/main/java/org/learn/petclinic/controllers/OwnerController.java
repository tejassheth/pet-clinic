package org.learn.petclinic.controllers;

import org.learn.petclinic.model.Owner;
import org.learn.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping({"","/","/index","/index.html"})
    public String processFindForm(Owner owner, BindingResult result, Map<String,Object> model){
        //allow parameter less GET request for /owners to return all
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        //find owners ny Last Name
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
        if(results.isEmpty()){
            //No owners found
            result.rejectValue("lastName","notFound","Not Found");
            return "owners/findOwners";
        }else if(results.size()==1){
            //1 Owner found
            owner= results.get(0);
            return "redirect:/owners/"+owner.getId();
        }else {
            //multiple owners found
            model.put("selections",results);
            return "owners/ownersList";
        }
    }
    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId){
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));
        return modelAndView;
    }
}
