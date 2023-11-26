package us.l4_4.dp1.end_of_line.gameplayer;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

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
    @ResponseStatus(HttpStatus.CREATED)
    public GamePlayer createGamePlayer(@RequestBody @Valid GamePlayerDTO newGamePlayerDTO){
        return gamePlayerService.createGamePlayer(newGamePlayerDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GamePlayer getGamePlayerById(@PathVariable Integer id){
        return gamePlayerService.getGamePlayerById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GamePlayer updateGamePlayer(@RequestBody @Valid GamePlayerDTO newGamePlayerDTO, @PathVariable Integer id){
        return gamePlayerService.updateGamePlayer(newGamePlayerDTO, id);
    }
    
}
