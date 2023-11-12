package org.springframework.samples.petclinic.game;

import java.util.Date;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity {

    /*TO DO Modificar la relación con Player para que aparezcan registrados 
    en la BD las IDs de los jugadores de una partida. Comprobar mediante la BD de H2.
    Chequear si las agragaciones se declaran como transient o usan el cascade = CascadeType. */

    @NotNull
    @Positive
    private Integer rounds;

    @ManyToOne
    @JoinColumn(name = "winner")
    private Player winner;

    @Column(name = "started")
    @NotNull
    private Date startedAt;

    @Column(name = "ended")
    private Date endedAt;

    @Transient
    private Message message;

    @Transient
    @Max(6)
    private Effect effect;

    /*@NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Card> cards;*/
}
