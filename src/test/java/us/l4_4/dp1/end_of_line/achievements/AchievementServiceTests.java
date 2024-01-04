package us.l4_4.dp1.end_of_line.achievements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import us.l4_4.dp1.end_of_line.achievement.Achievement;
import us.l4_4.dp1.end_of_line.achievement.AchievementService;
import us.l4_4.dp1.end_of_line.enums.Category;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase
public class AchievementServiceTests {

    @Autowired
    private AchievementService achievementService;

    private Achievement createAchievement(){
        Achievement achievement = new Achievement();
        achievement.setName("Test achievement");
        achievement.setDescription("This is a test achievement");
        achievement.setBadgeNotAchieved("https://example.com/badgeImage.jpg");
        achievement.setBadgeAchieved("https://example.com/badgeImage.jpg");
        achievement.setThreshold(100.0);
        achievement.setCategory(Category.GAMES_PLAYED);
        return this.achievementService.save(achievement);
    }

    @Test
    void shouldFindAllAchievements() {
        List<Achievement> achievements = (List<Achievement>) this.achievementService.findAll();
        assertEquals(9, achievements.size());
    }

    @Test
    void shouldFindAchievementById(){
        Achievement achievement = this.achievementService.findById(1);
        assertEquals("First crossing", achievement.getName());
    }

    @Test
    void shouldNotFindAchievementById(){
        assertThrows(ResourceNotFoundException.class, () -> this.achievementService.findById(404));
    }

    @Test
    @Transactional
    public void shouldInsertAchievement() {
        Iterable<Achievement> achievements = achievementService.findAll();
        long count = achievements.spliterator().getExactSizeIfKnown();
        createAchievement();
        Iterable<Achievement> achievements2 = achievementService.findAll();
        long count2 = achievements2.spliterator().getExactSizeIfKnown();
        assertEquals(count + 1, count2);
    } 

    @Test
    @Transactional
    public void shouldUpdateAchievement() {
        Achievement achievement = this.achievementService.findById(1);
        achievement.setName("Updated name");
        achievement.setDescription("Updated description");
        achievementService.update(1, achievement);
        assertEquals("Updated name", achievement.getName());
        assertEquals("Updated description", achievement.getDescription());
    } 

    @Test
    @Transactional
    public void shouldDeleteAchievement() {
        Achievement achievement = createAchievement();
        Iterable<Achievement> achievements = achievementService.findAll();
        long count = achievements.spliterator().getExactSizeIfKnown();
        achievementService.delete(achievement.getId());
        Iterable<Achievement> achievements2 = achievementService.findAll();
        long count2 = achievements2.spliterator().getExactSizeIfKnown();
        assertEquals(count - 1, count2);
    } 

}
