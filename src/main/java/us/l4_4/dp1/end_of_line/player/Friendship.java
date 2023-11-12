package us.l4_4.dp1.end_of_line.player;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "friendships")
public class Friendship extends BaseEntity{

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "sender")
    Player sender;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "receiver")
    Player receiver;

    @NotNull
    @Enumerated(EnumType.STRING)
    FriendStatus friendState;
}
