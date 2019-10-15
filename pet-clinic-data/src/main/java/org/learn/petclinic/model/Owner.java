package org.learn.petclinic.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Setter
@Getter
public class Owner extends Person {
    private Set<Pet> pets;
    private String address;
    private String city;
    private String telephone;
}
