package us.l4_4.dp1.end_of_line.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardDTO;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Hability;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;


@SpringBootTest
@AutoConfigureTestDatabase
class GameServiceTests {

    @Autowired
    private GameService gameService;
    @Autowired
    private GamePlayerService gpService;
    @Autowired
    private CardService cardService;
    private EffectDTO effectDTO;


    @Test
    public void shouldFindAllGames() {
        Iterable<Game> allGames = this.gameService.findAllGames();
        long count = StreamSupport.stream(allGames.spliterator(), false).count();
        assertNotEquals(0, count);
    }

    @Test
    void shouldFindGameById() {
        Game game = gameService.findById(1);
        assertEquals(16, game.getRound());
        assertEquals(1, game.getId());
        assertEquals(6, game.getWinner().getId());
    }

    @Test
    void shouldNotFindGameById() {
        assertThrows(ResourceNotFoundException.class, () -> gameService.findById(100000));
    }
    @Test
    void shouldUpdateGameEffect() {
        effectDTO = new EffectDTO();
        effectDTO.setEffect("REVERSE");
        Game game = gameService.createNewGame(22, 23, Color.RED, Color.BLUE);
        game.setRound(9);
        gameService.save(game);
        Game g = gameService.updateGameEffect(game.getId(), effectDTO);
        assertEquals(Hability.REVERSE, g.getEffect());
        game.setRound(1);
        game.setEffect(Hability.NONE);
        gameService.save(game);
        
    }

    @Test
    @Transactional
    public void shouldCreateNewGame() {
        Game game = gameService.createNewGame(3, 5, Color.RED, Color.BLUE);
        GamePlayer gp1 = game.getGamePlayers().get(0);
        GamePlayer gp2 = game.getGamePlayers().get(1);
        Integer nGamePlayers = game.getGamePlayers().size();
        assertNotEquals(null, game);
        assertNotEquals(null, game.getStartedAt());
        assertEquals(1, game.getRound());
        assertEquals(null, game.getWinner());
        assertEquals(null, game.getEndedAt());
        assertEquals(Hability.NONE, game.getEffect());
        assertEquals(2, nGamePlayers);
        assertEquals(25, gp1.getCards().size());
        assertEquals(25, gp2.getCards().size());

        assertEquals(Color.RED, gp1.getColor());
        assertEquals(3, gp1.getEnergy());
        assertEquals(3, gp1.getPlayer().getId());

        assertEquals(Color.BLUE, gp2.getColor());
        assertEquals(3, gp2.getEnergy());
        assertEquals(5, gp2.getPlayer().getId());

        for (Card card : gp1.getCards()) {
            if (!card.getExit().equals(Exit.START)) {
                assertEquals(Color.RED, card.getColor());
                assertEquals(null, card.getRow());
                assertEquals(null, card.getColumn());
                assertEquals(Orientation.S, card.getOrientation());
                assertEquals(null, card.getIsTemplate());
                assertEquals(null, card.getUpdatedAt());
            } else {
                assertEquals(4, card.getRow());
                assertEquals(2, card.getColumn());
            }
        }
        for (Card card : gp2.getCards()) {
            if (!card.getExit().equals(Exit.START)) {
                assertEquals(Color.BLUE, card.getColor());
                assertEquals(null, card.getRow());
                assertEquals(null, card.getColumn());
                assertEquals(Orientation.S, card.getOrientation());
                assertEquals(null, card.getIsTemplate());
                assertEquals(null, card.getUpdatedAt());
            } else {

                assertEquals(4, card.getRow());
                assertEquals(4, card.getColumn());
            }
        }
    }

    @Test
    void shouldCheckIfOnePlayerCanPlayAnotherGame() {
        Boolean canPlay2 = gameService.checkOnlyOneGameForEachPlayer(5);
        assertEquals(false, canPlay2);
    }

    @Test
    @Transactional
    void shouldCheckThatGiveFiveRamdomCardsToAGamePlayerGiven() {
        Game game = gameService.createNewGame(11, 12, Color.RED, Color.BLUE);
        GamePlayer gp1 = game.getGamePlayers().get(0);
        List<Card> player1Cards = gp1.getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        assertEquals(5, player1Cards.size());
    }

    @Test
    void shouldFindNotEndedGamesByPlayerId() {
        List<Game> games = gameService.findNotEndedGamesByPlayerId(7);
        assertEquals(1, games.size());

    }

    @Test
    void shouldNotFindNotEndedGamesByPlayerId() {
        List<Game> games = gameService.findNotEndedGamesByPlayerId(1);
        assertEquals(0, games.size());
    }

    @Test
    void shouldFindAllGamesByPlayerId() {
        List<Game> games = gameService.findAllGamesByPlayerId(7);
        assertEquals(true, games.size() > 3);
    }



    @Test
    @Transactional
    void shouldExtraGas() {
        Game game = gameService.createNewGame(9, 10, Color.RED, Color.BLUE);
        GamePlayer gp1 = game.getGamePlayers().get(0);
        List<Card> player1Cards = gp1.getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        assertEquals(5, player1Cards.size());
        Card c = gameService.extraGasEffect(game.getId());
        assertEquals(CardStatus.IN_HAND, c.getCardState());
    }

    @Test
    @Transactional
    void shouldChangeCardsInHand() {
        Game game = gameService.createNewGame(4, 6, Color.RED, Color.BLUE);
        GamePlayer gp1 = game.getGamePlayers().get(0);
        List<Card> player1Cards = gp1.getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        assertEquals(5, player1Cards.size());
        List<Card> cartas = gameService.changeCardsInHand(game.getId());
        assertEquals(5, cartas.size());
        assertNotEquals(player1Cards, cartas);

    }

    @Test
    @Transactional
    void shouldFindPosiblePosition() {
        Game g = gameService.createNewGame(18, 19, Color.RED, Color.BLUE);
        List<String> posiciones = gameService.findPosiblePositionOfAGamePlayerGiven(g.getId(), g.getGamePlayers().get(0).getId());
        String[] partes = posiciones.get(0).split(",");
        Integer row = Integer.parseInt(partes[1]);
        Integer column = Integer.parseInt(partes[0]);
        assertEquals(3, row);
        assertEquals(2, column);
        assertEquals(partes[2], "S");
        assertEquals(1, posiciones.size());

    }
    @Test
    @Transactional
    void shouldFindNoPosiblePosition() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setRow(3);
        cardDTO.setColumn(2);
        cardDTO.setOrientation("N");
        cardService.update(226, cardDTO);
        List<String> posiciones = gameService.findPosiblePositionOfAGamePlayerGiven(17, 33);
        assertEquals(0, posiciones.size());

    }

    @Test
    void shouldUpdateGameTurn() {
        Game game = gameService.createNewGame(20, 21, Color.RED, Color.BLUE);
        assertEquals(1, game.getRound());
        CardDTO cardDTO = new CardDTO();
        cardDTO.setRow(3);
        cardDTO.setColumn(2);
        cardDTO.setOrientation("N");
        cardService.update(226, cardDTO);
        Game updatedGame = gameService.updateGameTurn(game.getId());
        assertEquals(2, updatedGame.getRound());
        assertNotEquals(game.getGamePlayerTurnId(), updatedGame.getGamePlayerTurnId());
        assertEquals(Hability.NONE, updatedGame.getEffect());
        GamePlayer gp1 = game.getGamePlayers().get(0);
        List<Card> player1Cards = gp1.getCards().stream()
                .filter(card -> card.getCardState() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        assertEquals(5, player1Cards.size());
    }	



    @Test
    @Transactional
    void shouldSaveAGame() {

        Game g = new Game();
        g.setRound(1);
        Date d = Date.from(java.time.Instant.now());
        g.setStartedAt(d);
        g.setEffect(Hability.NONE);
        g.setGamePlayers(new ArrayList<GamePlayer>());
        Game updatedGame = gameService.save(g);
        assertEquals(1, updatedGame.getRound());
        assertEquals(Hability.NONE, updatedGame.getEffect());
        assertEquals(d, updatedGame.getStartedAt());
    }

    @Test
    @Transactional
    void shouldDeleteGame() {
        Game g = gameService.createNewGame(24, 25, Color.RED, Color.BLUE);
        assertEquals(1, g.getRound());
        assertNotEquals(null, g);
        gameService.deleteGame(g.getId(), g.getGamePlayers().get(0).getId());
        assertThrows(ResourceNotFoundException.class, () -> gameService.findById(g.getId()));
    }
    @Test
    void shouldCalculateStatistics(){
        Map<String,String> globalStats = gameService.calculateStatistics();
        assertEquals(globalStats.get("totalPlayers"),"13");
        assertEquals(globalStats.get("gamesFinished"),"16");
        assertEquals(globalStats.get("gamesPending"), "3");
        assertEquals(globalStats.get("avgGames"), "1,5");
        assertEquals(globalStats.get("totalGames"), "19");
        assertEquals(globalStats.get("mostUsedColor"), "RED");
        assertEquals(globalStats.get("maxRounds"), "27");
        assertEquals(globalStats.get("minRounds"), "16");
        assertEquals(globalStats.get("avgRounds"), "21,9");
        assertEquals(globalStats.get("maxGamesPlayed"), "12");
        assertEquals(globalStats.get("minGamesPlayed"), "1");
        assertEquals(globalStats.get("averageEnergyUsed"), "0,85");
        assertEquals(globalStats.get("averageGameDuration"), "0h 24m 50s");
        assertEquals(globalStats.get("maxGameDuration"), "0h 37m 0s");
        assertEquals(globalStats.get("minGameDuration"), "0h 15m 49s");
        assertEquals(globalStats.get("totalGameDuration"), "6h 37m 29s");
    }
}
