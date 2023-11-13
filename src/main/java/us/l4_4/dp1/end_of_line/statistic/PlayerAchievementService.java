package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerAchievementService {

    PlayerAchievementRepository repo;

    @Autowired
    public PlayerAchievementService(PlayerAchievementRepository repo){
        this.repo=repo;
    }

    @Transactional(readOnly = true)
    public List<PlayerAchievement> getPlayerAchievements(){
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public PlayerAchievement getPlayerAchievementById(int id){
        return repo.findById(id);
    }

    @Transactional
    public PlayerAchievement savePlayerAchievement(PlayerAchievement newPlayerAchievement) {
        return repo.save(newPlayerAchievement);
    }

    @Transactional
    public void deletePlayerAchievementById(int id){
        repo.deleteById(id);
    }
    
}
