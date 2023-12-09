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
    public Iterable<PlayerAchievement> findAll(){
        return playerAchievementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PlayerAchievement findById(Integer id){
        return playerAchievementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PlayerAchievement", "id", id));
    }

    @Transactional
    public PlayerAchievement save(PlayerAchievement playerAchievement) {
        return playerAchievementRepository.save(playerAchievement);
    }

    @Transactional
    public void delete(Integer id){
        playerAchievementRepository.deleteById(id);
    }
}
