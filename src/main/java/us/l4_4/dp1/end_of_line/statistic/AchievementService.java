package us.l4_4.dp1.end_of_line.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@Service
public class AchievementService {

    AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository){
        this.achievementRepository=achievementRepository;
    }

    @Transactional(readOnly = true)    
    public Iterable<Achievement> findAllAchievements(){
        return achievementRepository.findAll();
    }

    @Transactional(readOnly = true)    
    public Achievement findAchiviementById(Integer id){
        return achievementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));
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
    public Achievement findAchievementByName(String name){
        return achievementRepository.findByName(name);
    }
}