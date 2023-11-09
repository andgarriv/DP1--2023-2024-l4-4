package org.springframework.samples.petclinic.game;

import java.util.Date;
import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @ManyToOne
    @NotNull
    private List<Player> players;

    @Column(name = "rounds")
    @NotNull
    @Positive
    private Integer rounds;

    @ManyToOne
    private Player winner;

    @Column(name = "started")
    @NotNull
    private Date startedAt;

    @Column(name = "ended")
    private Date endedAt;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
    @NotNull
    private GameStatus gameState;

    @Transient
    private Message message;

    @Transient
    @Max(6)
    private Effect effect;
}
