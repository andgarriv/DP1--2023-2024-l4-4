package us.l4_4.dp1.end_of_line.gameplayer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/gameplayers")
@Tag(name= "GamePlayers", description = "API for the management of GamePlayers")
@SecurityRequirement(name = "bearerAuth")
public class GamePlayerController {

    private GamePlayerService gamePlayerService;

    public GamePlayerController(GamePlayerService gamePlayerService){
        this.gamePlayerService = gamePlayerService;
    }

    @PostMapping
    public GamePlayer createGamePlayer(@RequestBody @Valid GamePlayerDTO newGamePlayerDTO){
        return gamePlayerService.createGamePlayer(newGamePlayerDTO);
    }
    
}
