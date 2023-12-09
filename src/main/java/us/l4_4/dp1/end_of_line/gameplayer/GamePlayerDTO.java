package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Color;

@Getter
@Setter
public class GamePlayerDTO{

    @Id
    Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    Color color;

    @NotNull 
    @Range(min = 0, max = 3)
    Integer energy;

    @NotNull
    Integer player_id;

    List<Integer> cards_ids;
}
