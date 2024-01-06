package us.l4_4.dp1.end_of_line.message;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;

@Getter
@Setter
public class MessageDTO {

    @NotNull
    Integer gameId;

    @NotNull
    @Enumerated(EnumType.STRING)
    Color color;

    @NotNull
    @Enumerated(EnumType.STRING)
    Reaction reaction;
}
