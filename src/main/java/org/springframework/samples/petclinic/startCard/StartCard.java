package org.springframework.samples.petclinic.startCard;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class StartCard extends BaseEntity {
    Integer exit;
}
