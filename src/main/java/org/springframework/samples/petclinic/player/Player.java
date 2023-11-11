package org.springframework.samples.petclinic.player;

import java.util.List;

import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends User{
    
    @NotBlank
    @Column(unique = true)
    @Size(min = 5, max = 15)
    String nickname;

    @NotBlank
    @URL
    String avatar;
    
    //@NotNull
    //@ManyToMany(mappedBy = "players")
    //List<Game> game;
}