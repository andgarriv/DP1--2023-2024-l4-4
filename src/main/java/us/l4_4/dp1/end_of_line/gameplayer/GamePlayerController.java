package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GamePlayer> findGamePlayersByGameId(@PathVariable Integer id) {
        return gamePlayerService.findGamePlayersByGameId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GamePlayer create(@RequestBody @Valid GamePlayerDTO gamePlayerDTO){
        return gamePlayerService.create(gamePlayerDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GamePlayer findById(@PathVariable Integer id){
        return gamePlayerService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GamePlayer update(@RequestBody @Valid GamePlayerDTO gamePlayerDTO, @PathVariable Integer id){
        return gamePlayerService.update(gamePlayerDTO, id);
    }
}
