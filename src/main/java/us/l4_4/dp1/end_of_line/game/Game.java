package us.l4_4.dp1.end_of_line.game;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.effect.Effect;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.model.BaseEntity;
import us.l4_4.dp1.end_of_line.player.Player;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity {

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

    @OneToMany
    private List<Message> message;

    @Transient
    @Max(6)
    private Effect effect;

    @NotNull
    @OneToMany
    private List<GamePlayer> gamePlayers;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Card> cards;
}
