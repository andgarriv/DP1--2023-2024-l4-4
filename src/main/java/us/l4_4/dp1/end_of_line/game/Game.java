package us.l4_4.dp1.end_of_line.game;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Hability;
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
    @Min(0)
    private Integer round;

    @ManyToOne
    @JoinColumn(name = "winner", nullable = true, 
                foreignKey = @ForeignKey(name = "FK_player", value = ConstraintMode.NO_CONSTRAINT))
    private Player winner;

    @Column(name = "started")
    @NotNull
    private Date startedAt;

    @Column(name = "ended")
    private Date endedAt;

    @OneToMany
    private List<Message> message;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Hability effect;

    @NotNull
    @OneToMany
    private List<GamePlayer> gamePlayers;

    private Integer gamePlayerTurnId;
}
