package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<PlayerAchievement>> findAll() {
        return new ResponseEntity<>((List<PlayerAchievement>) playerAchievementService.getPlayerAchievements(), HttpStatus.OK);
    }
    
}
