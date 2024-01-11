package us.l4_4.dp1.end_of_line.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageService;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Games", description = "API for the management of Games")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    CardService cardService;

    @Autowired
    GamePlayerService gamePlayerService;

    @Autowired
    MessageService messageService;

    @GetMapping("/players/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findAllGamesByPlayerId(@PathVariable Integer id) {
        return gameService.findAllGamesByPlayerId(id);
    }

    @GetMapping("/players/{id}/notended")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findNotEndedGamesByPlayerId(@PathVariable Integer id) {
        return gameService.findNotEndedGamesByPlayerId(id);
    }

    @GetMapping("/{id}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> findAllCardsOfGame(@PathVariable Integer id){
        return cardService.findAllCardsOfGame(id);
    }

    @GetMapping("/{id}/gameplayers")
    @ResponseStatus(HttpStatus.OK)
    public List<GamePlayer> findGamePlayersByGameId(@PathVariable Integer id) {
        return gamePlayerService.findGamePlayersByGameId(id);
    }

    @GetMapping("/{id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> findAllMessagesByGameId(@PathVariable Integer id) {
        return messageService.findAllMessagesByGameId(id);
    }

    @GetMapping("/all")
    public Iterable<Game> findAllGames() {
        return gameService.findAllGames();
    }

    @DeleteMapping("/{gameId}/{gamePlayerId}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer gameId, @PathVariable Integer gamePlayerId) {
        try {
            gameService.deleteGame(gameId, gamePlayerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@RequestBody @Valid NewGameRequest newGameRequest) {
        Integer player1_id = newGameRequest.getPlayer1Id();
        Integer player2_id = newGameRequest.getPlayer2Id();
        Color player1_color_enum = Color.valueOf(newGameRequest.getPlayer1Color());
        Color player2_color_enum = Color.valueOf(newGameRequest.getPlayer2Color());
        return gameService.createNewGame(player1_id, player2_id, player1_color_enum, player2_color_enum);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game update(@PathVariable Integer id, @RequestBody @Valid GameDTO gameDTO) {
        return gameService.updateGame(id, gameDTO);
    }

    @PutMapping("/{gameId}/test")
    @ResponseStatus(HttpStatus.OK)
    public Game pruebaTurn(@PathVariable Integer gameId) {
    return gameService.updateGameTurn(gameId);
    }

    @PutMapping("/{gameId}/effect")
    @ResponseStatus(HttpStatus.OK)
    public Game changeEffect(@PathVariable Integer gameId, @RequestBody @Valid ChangeEffectRequest changeEffectRequest) {
        return gameService.updateGameEffect(gameId, changeEffectRequest);
    }

    @PutMapping("/{gameId}/changeCardsInHand")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> changeCardsInHand(@PathVariable Integer gameId) {
        return gameService.changeCardsInHand(gameId);
    }

    @GetMapping("/{gameId}/gameplayers/{gamePlayerId}/getCardsPositions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCardsPositions(@PathVariable Integer gameId, @PathVariable Integer gamePlayerId) {
        return gameService.findPosiblePositionOfAGamePlayerGiven(gamePlayerId, gameId, false);
    }
    
}

