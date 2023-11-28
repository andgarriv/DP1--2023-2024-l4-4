package us.l4_4.dp1.end_of_line.friendship;

import jakarta.persistence.Column;
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
    @Column(name = "sender")
    Integer sender_id;

    @NotNull
    @Column(name = "receiver")
    Integer receiver_id;

    @NotNull
    @Enumerated(EnumType.STRING)
    FriendStatus friendship_state;
}
