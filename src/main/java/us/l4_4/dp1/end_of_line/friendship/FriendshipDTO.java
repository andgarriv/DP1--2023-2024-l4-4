package us.l4_4.dp1.end_of_line.friendship;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;

@Getter
@Setter
public class FriendshipDTO {
    
    @Id
    Integer id;

    @NotNull
    Integer sender;

    @NotNull
    Integer receiver;

    @NotNull
    @Enumerated(EnumType.STRING)
    FriendStatus friendship_state;
}
