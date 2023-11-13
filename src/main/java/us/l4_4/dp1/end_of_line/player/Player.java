package us.l4_4.dp1.end_of_line.player;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.model.User;
import us.l4_4.dp1.end_of_line.statistic.PlayerAchievement;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends User{

    @NotBlank
    @URL
    String avatar;

    @OneToMany
    List<PlayerAchievement> playerAchievement;
}