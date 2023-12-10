package us.l4_4.dp1.end_of_line.friendship;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.model.BaseEntity;
import us.l4_4.dp1.end_of_line.player.Player;

@Getter
@Setter
@Entity

@Table(name = "friendships")
public class Friendship extends BaseEntity {

    @JsonBackReference("sentFriendships")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "sender")
    Player sender;

    @JsonBackReference("receivedFriendships")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "receiver")
    Player receiver;

    @NotNull
    @Enumerated(EnumType.STRING)
    FriendStatus friendState;
}

