package us.l4_4.dp1.end_of_line.game;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/all")
    public Iterable<Game> findAllGames() {
        return gameService.findAllGames();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game findById(@PathVariable Integer id) {
        return gameService.findById(id);
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

    @GetMapping("/{id}/gameplayers/{gamePlayerId}/cards/positions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> cardsPossiblePositions(@PathVariable Integer id, @PathVariable Integer gamePlayerId) {
        return gameService.findPosiblePositionOfAGamePlayerGiven(id, gamePlayerId);
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getGameStatistics() {
        return gameService.calculateStatistics();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@RequestBody @Valid NewGameDTO newGameDTO) {
        Integer player1Id = newGameDTO.getPlayer1Id();
        Integer player2Id = newGameDTO.getPlayer2Id();
        Color player1Color = Color.valueOf(newGameDTO.getPlayer1Color());
        Color player2Color = Color.valueOf(newGameDTO.getPlayer2Color());
        return gameService.createNewGame(player1Id, player2Id, player1Color, player2Color);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game update(@PathVariable Integer id) {
    return gameService.updateGameTurn(id);
    }

    @PutMapping("/{id}/effect")
    @ResponseStatus(HttpStatus.OK)
    public Game changeEffect(@PathVariable Integer id, @RequestBody @Valid EffectDTO effectDTO) {
        return gameService.updateGameEffect(id, effectDTO);
    }

    @PutMapping("/{id}/cards/discard")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> changeCardsInHand(@PathVariable Integer id) {
        return gameService.changeCardsInHand(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        gameService.delete(id);
    }

    @DeleteMapping("/{id}/gameplayers/{gamePlayerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable Integer id, @PathVariable Integer gamePlayerId) {
        gameService.deleteGame(id, gamePlayerId);
    }
}