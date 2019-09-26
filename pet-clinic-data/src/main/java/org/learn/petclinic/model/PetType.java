package org.learn.petclinic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetType extends BaseEntity {
    private String name;
}