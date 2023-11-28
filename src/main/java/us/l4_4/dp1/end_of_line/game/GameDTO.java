package us.l4_4.dp1.end_of_line.game;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {

    @Id
    Integer id;

    @NotNull
    @Min(0)
    Integer rounds;

    Integer winner_id;

    @NotNull
    Date startedAt;

    Date endedAt;

    List<Integer> message_id;

    Integer effect_id;

    @NotNull
    List<Integer> gamePlayers_ids;
}
