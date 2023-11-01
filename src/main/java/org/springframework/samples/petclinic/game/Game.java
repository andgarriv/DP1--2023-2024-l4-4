package org.springframework.samples.petclinic.game;

import java.util.Date;
import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{
    
    @Column(name = "players")
    @NotNull
    private List<Integer> players;
    
    @Column(name = "rounds")
    @NotNull
    private Integer rounds;

    @Column(name = "winner")
    private Integer winner;

    @Column(name = "started")
    @NotNull
    private Date startedAt;

    @Column(name = "ended")
    private Date endedAt;

    @Column(name = "game_state")
    @Enumerated(EnumType.STRING)
	@NotNull
	private GameStatus gameState;
}
