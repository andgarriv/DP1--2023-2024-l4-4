package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerAchievementService {

    PlayerAchievementRepository playerAchievementRepository;

    @Autowired
    public PlayerAchievementService(PlayerAchievementRepository playerAchievementRepository){
        this.playerAchievementRepository=playerAchievementRepository;
    }

    @Transactional(readOnly = true)
    public List<PlayerAchievement> getPlayerAchievements(){
        return playerAchievementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PlayerAchievement getPlayerAchievementById(int id){
        return playerAchievementRepository.findById(id);
    }

    @Transactional
    public PlayerAchievement savePlayerAchievement(PlayerAchievement newPlayerAchievement) {
        return playerAchievementRepository.save(newPlayerAchievement);
    }

    @Transactional
    public void deletePlayerAchievementById(int id){
        playerAchievementRepository.deleteById(id);
    }
    
}
