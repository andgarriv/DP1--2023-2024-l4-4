package us.l4_4.dp1.end_of_line.achievement;


import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Category;
import us.l4_4.dp1.end_of_line.model.NamedEntity;
import us.l4_4.dp1.end_of_line.playerachievement.PlayerAchievement;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends NamedEntity {

    @NotBlank
    @Size(min = 10, max = 150)
    private String description;

    @NotBlank
    @URL
    private String badgeNotAchieved;

    @NotBlank
    @URL
    private String badgeAchieved;

    @NotNull
    @Min(0)
    private double threshold;

    @Enumerated(EnumType.STRING)
    @NotNull
    Category category;

    @OneToMany()
    List<PlayerAchievement> playerAchievements;
}