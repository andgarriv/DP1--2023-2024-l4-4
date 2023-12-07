package us.l4_4.dp1.end_of_line.player;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.friendship.Friendship;
import us.l4_4.dp1.end_of_line.model.User;
import us.l4_4.dp1.end_of_line.playerachievement.PlayerAchievement;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends User{

    @NotBlank
    @URL
    String avatar;

    @JsonBackReference
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    List<Friendship> sentFriendships;

    @JsonBackReference
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    List<Friendship> receivedFriendships;

    @OneToMany(cascade = CascadeType.REMOVE)
    List<PlayerAchievement> playerAchievement;

}