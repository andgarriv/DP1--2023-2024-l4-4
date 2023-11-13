package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.l4_4.dp1.end_of_line.player.Player;

@Repository
public interface PlayerAchievementRepository extends CrudRepository<PlayerAchievement, Integer>{

    List<PlayerAchievement> findAll();

    @Query("SELECT p FROM PlayerAchievement p WHERE p.id=:id")
    PlayerAchievement findById(int id);
    
} 
    

