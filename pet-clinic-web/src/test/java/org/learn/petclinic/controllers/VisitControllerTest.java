package org.learn.petclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learn.petclinic.model.Pet;
import org.learn.petclinic.model.Visit;
import org.learn.petclinic.services.OwnerService;
import org.learn.petclinic.services.PetService;
import org.learn.petclinic.services.VisitService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @InjectMocks
    private VisitController visitController;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetService petService;

    @Mock
    private VisitService visitService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }

    @Test
    void testInitVisitCreationForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().visits(new HashSet<Visit>()).build());

        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"));

        verify(petService,times(1)).findById(anyLong());
    }

    @Test
    void testProcessVisitCreationForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().visits(new HashSet<Visit>()).build());

        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"))
                .andExpect(model().attributeExists("pet"));

        verify(petService,times(1)).findById(anyLong());
        verify(visitService,times(1)).save(any());
    }
}