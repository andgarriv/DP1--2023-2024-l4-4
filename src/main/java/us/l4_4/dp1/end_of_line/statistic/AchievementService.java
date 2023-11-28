package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
public class AchievementService {

    AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository){
        this.achievementRepository=achievementRepository;
    }

    @Transactional(readOnly = true)    
    public List<Achievement> getAchievements(){
        return achievementRepository.findAll();
    }

    @Transactional(readOnly = true)    
    public Achievement getById(int id){
        Optional<Achievement> result=achievementRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional
    public Achievement saveAchievement(@Valid Achievement newAchievement) {
        return achievementRepository.save(newAchievement);
    }

    @Transactional
    public void deleteAchievementById(int id){
        achievementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementByName(String name){
        return achievementRepository.findByName(name);
    }
}