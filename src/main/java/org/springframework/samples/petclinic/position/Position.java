package org.springframework.samples.petclinic.position;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Position extends BaseEntity {
    
    Integer row;
    Integer colum;
    
}
