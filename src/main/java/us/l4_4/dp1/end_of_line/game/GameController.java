package us.l4_4.dp1.end_of_line.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name= "Games", description = "API for the management of Games")
public class GameController {

    @Autowired
    GameService gameService;

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public Game creatGame(@RequestBody @Valid Game game) {
    //     gameService.save(game);
    //     return game;
    // }
    @GetMapping("/player")
    public List<Game> getGamesByPlayerId() {
        return gameService.getAllGames();
    }

    @GetMapping("/admin")
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody @Valid GameDTO gameDTO) {
        return gameService.createGame(gameDTO);
    }
    
}
