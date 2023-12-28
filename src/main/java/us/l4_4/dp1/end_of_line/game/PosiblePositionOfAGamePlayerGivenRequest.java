package us.l4_4.dp1.end_of_line.game;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosiblePositionOfAGamePlayerGivenRequest {

    @NotNull
    @Min(0)
    private Integer gamePlayerId;

    @NotNull
    @Min(0)
    private Integer gameId;
    
}
