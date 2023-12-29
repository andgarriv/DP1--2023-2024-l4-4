package us.l4_4.dp1.end_of_line.game;


import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    public void shouldFindAllGames() {
        Iterable<Game> allGames = this.gameService.findAllGames();
        long count = StreamSupport.stream(allGames.spliterator(), false).count();
        assertNotEquals(0, count);
    }

    @Test 
    void shouldFindGameById(){
        Game game = gameService.findById(1);
        assertEquals(16, game.getRound());
    }

    /* @Test
    public void shouldCreateGame() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setRounds(0);
        gameDTO.setStartedAt(Date.valueOf("2021-01-01"));
        gameDTO.setEndedAt(null);
        gameDTO.setWinner_id(null);
        gameDTO.setMessage_id(null);
        gameDTO.setEffect(Hability.NONE);
        gameDTO.setGamePlayers_ids(List.of(3,4));
        Game game = gameService.createGame(gameDTO);
        assertNotEquals(null, game);
    }

    @Test
    public void shouldCreateNewGame() {
        Integer player1_id = 3;
        Integer player2_id = 4;
        Color player1_color = Color.RED;
        Color player2_color = Color.RED;
        Game game = gameService.createNewGame(player1_id, player2_id, player1_color, player2_color);
        assertNotEquals(null, game);
    }    */ 
}
