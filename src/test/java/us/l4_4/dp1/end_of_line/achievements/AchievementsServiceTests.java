package us.l4_4.dp1.end_of_line.achievements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.statistic.Achievement;
import us.l4_4.dp1.end_of_line.statistic.AchievementRepository;
import us.l4_4.dp1.end_of_line.statistic.AchievementService;
import us.l4_4.dp1.end_of_line.statistic.Metric;

@SpringBootTest
@AutoConfigureTestDatabase
public class AchievementsServiceTests {

    private AchievementService achievementService;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    public AchievementsServiceTests(AchievementService achievementService){
        this.achievementService=achievementService;
    }    


    @Test
     void shouldFindAllAchievements() {
        List<Achievement> achievements =  this.achievementService.getAchievements();
        assertEquals(3, achievements.size());
    }

    @Test
    @Transactional
    public void shouldCreateAchievement() {
        // Crear un logro de prueba con propiedades válidas
        Achievement newAchievement = new Achievement();
        newAchievement.setName("Valid name");
        newAchievement.setDescription("This is a valid description with more than 10 characters.");
        newAchievement.setBadgeImage("https://example.com/badgeImage.jpg");
        newAchievement.setThreshold(100.0);
        newAchievement.setMetric(Metric.GAMES_PLAYED);
        // Llamada al método que se está probando
        Achievement savedAchievement = achievementService.saveAchievement(newAchievement);
        // Verificación
        assertEquals(newAchievement, savedAchievement);
    }

    @Test
    @Transactional
    public void shouldEditAchievement() {
        // Crear un logro de prueba
        Achievement existingAchievement = new Achievement();
        existingAchievement.setId(1); 
        existingAchievement.setName("Original Name");
        existingAchievement.setDescription("Original Description");
        existingAchievement.setBadgeImage("https://example.com/originalBadgeImage.jpg");
        existingAchievement.setThreshold(100.0);
        existingAchievement.setMetric(Metric.GAMES_PLAYED); // Reemplazar SOME_METRIC con un valor válido de tu Enum Metric

        // Suponer que este es el logro actualizado
        Achievement updatedAchievement = new Achievement();
        updatedAchievement.setId(1); 
        updatedAchievement.setName("Updated Name");
        updatedAchievement.setDescription("Updated Description");
        updatedAchievement.setBadgeImage("https://example.com/updatedBadgeImage.jpg");
        updatedAchievement.setThreshold(200.0);
        updatedAchievement.setMetric(Metric.TOTAL_PLAY_TIME);

        // Llamada al método que se está probando
        Achievement result = achievementService.saveAchievement(updatedAchievement);

        // Verificación
        assertEquals(updatedAchievement.getDescription(), result.getDescription());
        assertEquals(updatedAchievement.getBadgeImage(), result.getBadgeImage());
        assertEquals(updatedAchievement.getThreshold(), result.getThreshold(), 0.01);
        assertEquals(updatedAchievement.getMetric(), result.getMetric());
    }
    
}
