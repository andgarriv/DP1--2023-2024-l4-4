package us.l4_4.dp1.end_of_line.playerachievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/playerachievements")
@Tag(name = "PlayerAchievements", description = "The Player Achievements management API")
@SecurityRequirement(name = "bearerAuth")
public class PlayerAchievementController {

    private final PlayerAchievementService playerAchievementService;

    @Autowired
    public PlayerAchievementController(PlayerAchievementService playerAchievementService) {
        this.playerAchievementService = playerAchievementService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<PlayerAchievement> getAll() {
        return playerAchievementService.findAll();
    }
}
