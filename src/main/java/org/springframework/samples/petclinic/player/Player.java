package org.springframework.samples.petclinic.player;

import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends User{

    @NotBlank
    @URL
    String avatar;
}