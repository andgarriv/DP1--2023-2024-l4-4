package us.l4_4.dp1.end_of_line.card;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {

    @NotNull
    private Integer row;

    @NotNull
    private Integer column;

    @NotNull
    private String orientation;
}
