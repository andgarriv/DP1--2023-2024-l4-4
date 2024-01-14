package us.l4_4.dp1.end_of_line.playerachievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase
public class PlayerAchievementServiceTests {
    
    @Autowired
    private PlayerAchievementService playerAchievementService;

    private PlayerAchievement createPlayerAchievement(){
        LocalDate achieveAt = LocalDate.of(2023, 2, 12);

        PlayerAchievement playerAchievement = new PlayerAchievement();
        playerAchievement.setAchieveAt(achieveAt);

        return this.playerAchievementService.save(playerAchievement);
    }

    @Test
    public void shouldFindAllPlayerAchievements() {
        List<PlayerAchievement> playerAchievements = (List<PlayerAchievement>) this.playerAchievementService.findAll();
        assertEquals(32, playerAchievements.size());
    }

    @Test
    public void shouldFindPlayerAchievementById() {
        PlayerAchievement playerAchievement = this.playerAchievementService.findById(1);
        assertEquals(LocalDate.of(2023, 9, 01), playerAchievement.getAchieveAt());
    }

    @Test
    public void shouldNotFindPlayerAchievementById() {
        assertThrows(ResourceNotFoundException.class, () -> this.playerAchievementService.findById(404));
    }

    @Test
    public void shouldInsertPlayerAchievement() {
        Integer count = ((Collection<PlayerAchievement>)this.playerAchievementService.findAll()).size();
        createPlayerAchievement();
        Integer finalCount = ((Collection<PlayerAchievement>)this.playerAchievementService.findAll()).size();
        assertEquals(count + 1, finalCount);
    }

    @Test
    public void shouldDeletePlayerAchievement() {
        Integer count = ((Collection<PlayerAchievement>)this.playerAchievementService.findAll()).size();
        PlayerAchievement playerAchievement = createPlayerAchievement();

        Integer count2 = ((Collection<PlayerAchievement>)this.playerAchievementService.findAll()).size();
        assertEquals(count + 1, count2);

        this.playerAchievementService.delete(playerAchievement.getId());
        Integer finalCount = ((Collection<PlayerAchievement>)this.playerAchievementService.findAll()).size();
        assertEquals(count, finalCount);
    }

}
