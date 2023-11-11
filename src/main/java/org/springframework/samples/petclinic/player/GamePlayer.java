package org.springframework.samples.petclinic.player;



import org.hibernate.validator.constraints.Range;

import org.springframework.samples.petclinic.game.Color;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotNull;


@Entity
public class GamePlayer extends BaseEntity {

    /*TO DO. Arreglar la relación ya que no sólo hay que incluir las IDs de las dos entidades sino que también
     * hay que incluir dos propiedades más: el color y la energía. llamar a la tabla "game_players" y 
     * comprobar mediante la BD de H2.
     */

    @NotNull
    @Enumerated(EnumType.STRING)
    Color color;

    @NotNull 
    @Range(min = 0, max = 3)
    Integer energy;

    @NotNull
    @ManyToOne
    Player player;


}
