package us.l4_4.dp1.end_of_line.achievements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.achievement.Achievement;
import us.l4_4.dp1.end_of_line.achievement.AchievementRepository;
import us.l4_4.dp1.end_of_line.achievement.AchievementService;
import us.l4_4.dp1.end_of_line.enums.Category;

@SpringBootTest
@AutoConfigureTestDatabase
public class AchievementServiceTests {

    private AchievementService achievementService;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    public AchievementServiceTests(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @Test
    void shouldFindAllAchievements() {
        Iterable<Achievement> achievements = this.achievementService.findAll();
        long count = StreamSupport.stream(achievements.spliterator(), false).count();
        assertEquals(3, count);
    }

    @Test
    @Transactional
    public void shouldFindAchievementById() {
        // Crear un logro de prueba
        Achievement existingAchievement = new Achievement();
        existingAchievement.setId(1);
        existingAchievement.setName("Original Name");
        existingAchievement.setDescription("Original Description");
        existingAchievement.setBadgeImage("https://example.com/originalBadgeImage.jpg");
        existingAchievement.setThreshold(100.0);
        existingAchievement.setCategory(Category.GAMES_PLAYED);
        achievementService.save(existingAchievement);

        // Llamada al método que se está probando
        Achievement result = achievementService.findById(existingAchievement.getId());

        // Verificación
        assertEquals(existingAchievement.getName(), result.getName());
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
        newAchievement.setCategory(Category.GAMES_PLAYED);
        // Llamada al método que se está probando
        Achievement savedAchievement = achievementService.save(newAchievement);
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
        existingAchievement.setCategory(Category.GAMES_PLAYED);

        // Suponer que este es el logro actualizado
        Achievement updatedAchievement = new Achievement();
        updatedAchievement.setId(1);
        updatedAchievement.setName("Updated Name");
        updatedAchievement.setDescription("Updated Description");
        updatedAchievement.setBadgeImage("https://example.com/updatedBadgeImage.jpg");
        updatedAchievement.setThreshold(200.0);
        updatedAchievement.setCategory(Category.TOTAL_PLAY_TIME);

        // Llamada al método que se está probando
        Achievement result = achievementService.save(updatedAchievement);

        // Verificación
        assertEquals(updatedAchievement.getDescription(), result.getDescription());
        assertEquals(updatedAchievement.getBadgeImage(), result.getBadgeImage());
        assertEquals(updatedAchievement.getThreshold(), result.getThreshold(), 0.01);
        assertEquals(updatedAchievement.getCategory(), result.getCategory());
    }

    @Test
    @Transactional
    public void shouldDeleteAchievement() {
        // Crear un logro de prueba
        Achievement existingAchievement = new Achievement();
        existingAchievement.setId(1);
        existingAchievement.setName("Original Name");
        existingAchievement.setDescription("Original Description");
        existingAchievement.setBadgeImage("https://example.com/originalBadgeImage.jpg");
        existingAchievement.setThreshold(100.0);
        existingAchievement.setCategory(Category.GAMES_PLAYED);

        // Llamada al método que se está probando
        achievementService.delete(existingAchievement.getId());

        // Verificación
        assertNull(achievementRepository.findById(existingAchievement.getId()).orElse(null));
    }

}
