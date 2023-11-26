package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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

    //@NotNull
    //@Size(min = 23, max = 25)
    //TODO: Necesitamos que al crear un gameplayer se le asignen todas las cartas de su color
    List<Integer> cards_ids;

}
