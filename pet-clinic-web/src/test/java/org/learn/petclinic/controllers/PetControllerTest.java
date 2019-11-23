package org.learn.petclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learn.petclinic.model.Owner;
import org.learn.petclinic.model.Pet;
import org.learn.petclinic.model.PetType;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetService;
import org.learn.petclinic.services.PetTypeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;


    @InjectMocks
    PetController petController;

    Owner owner;
    Set<PetType> typeSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        owner = Owner.builder().id(1L).pets(new HashSet<>()).build();

        Collection<PetType> typeSet = new HashSet<>();
        typeSet.add(PetType.builder().name("Dog").build());
        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();
    }

    @Test
    void testInitPetCreationForm() throws Exception {
        Set<PetType> typeSet = new HashSet<>();
        typeSet.add(PetType.builder().name("Dog").build());

        when(petTypeService.findAll()).thenReturn(typeSet);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("owner"));

    }

    @Test
    void testProcessPetCreationForm() throws Exception {
        when(petTypeService.findAll()).thenReturn(typeSet);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }

    @Test
    void testInitUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().build());
        when(petTypeService.findAll()).thenReturn(typeSet);

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

    }

    @Test
    void testProcessUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(typeSet);

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(petService).save(any());
    }

    @Test
    void populatePetTypes() {
        when(petTypeService.findAll()).thenReturn(typeSet);
        Collection<PetType> petTypeSet = petController.populatePetTypes();
        assertEquals(typeSet, petTypeSet);
    }

    @Test
    void findOwner() {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        Owner foundOwner = petController.findOwner(1L);
        assertEquals(foundOwner, owner);
    }
}