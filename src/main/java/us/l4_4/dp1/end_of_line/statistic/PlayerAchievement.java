package us.l4_4.dp1.end_of_line.statistic;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.model.BaseEntity;

@Entity
@Setter
@Getter
@Table(name = "player_achievements")
public class PlayerAchievement extends BaseEntity{

       LocalDate achieveAt;  
       
       @ManyToOne(optional = false)
       Achievement achievement;
}
