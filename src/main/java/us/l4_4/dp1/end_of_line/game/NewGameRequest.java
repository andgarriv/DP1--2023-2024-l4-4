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
public class NewGameRequest {

    @NotNull
    @Min(0)
    private Integer player1Id;

    @NotNull
    @Min(0)
    private Integer player2Id;

    @NotNull
    private String player1Color;

    @NotNull
    private String player2Color;
}
