package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.model.BaseEntity;
import us.l4_4.dp1.end_of_line.player.Player;

@Entity
@Getter
@Setter
@Table(name = "game_players")
public class GamePlayer extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    Color color;

    @NotNull
    @Range(min = 0, max = 3)
    Integer energy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "player_id", nullable = true, foreignKey = @ForeignKey(name = "FK_player", value = ConstraintMode.NO_CONSTRAINT))
    Player player;

    @NotNull
    @OneToMany
    List<Card> cards;
}
