package us.l4_4.dp1.end_of_line.playerachievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@Service
public class PlayerAchievementService {

    PlayerAchievementRepository playerAchievementRepository;

    @Autowired
    public PlayerAchievementService(PlayerAchievementRepository playerAchievementRepository){
        this.playerAchievementRepository=playerAchievementRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<PlayerAchievement> findAllPlayerAchievements(){
        return playerAchievementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PlayerAchievement findPlayerAchievementById(Integer id){
        return playerAchievementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PlayerAchievement", "id", id));
    }

    @Transactional
    public PlayerAchievement savePlayerAchievement(PlayerAchievement playerAchievement) {
        return playerAchievementRepository.save(playerAchievement);
    }

    @Transactional
    public void deletePlayerAchievementById(Integer id){
        playerAchievementRepository.deleteById(id);
    }
}
