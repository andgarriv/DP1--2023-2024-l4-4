package us.l4_4.dp1.end_of_line.achievement;

import org.springframework.beans.BeanUtils;
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
    public Iterable<Achievement> findAll(){
        return achievementRepository.findAll();
    }

    @Transactional(readOnly = true)    
    public Achievement findById(Integer id) throws ResourceNotFoundException{
        return achievementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));
    }

    @Transactional
    public Achievement save(@Valid Achievement newAchievement) {
        return achievementRepository.save(newAchievement);
    }

    @Transactional
    public Achievement update(Integer id, Achievement achievement) {
        Achievement achievementToUpdate = findById(id);
        BeanUtils.copyProperties(achievement, achievementToUpdate, "id");
        return achievementRepository.save(achievementToUpdate);
    }

    @Transactional
    public void delete(Integer id){
        achievementRepository.deleteById(id);
    }
}