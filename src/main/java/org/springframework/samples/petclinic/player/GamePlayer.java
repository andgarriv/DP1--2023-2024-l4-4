package org.springframework.samples.petclinic.player;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.Color;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

public class GamePlayer {

    @NotNull
    Color color;

    @NotNull 
    @Range(min = 0, max = 3)
    Integer energy;

    @NotNull
    @OneToMany
    List<Card> cards;

    //@NotNull
    //@ManyToOne
    //Player player;
    
}
