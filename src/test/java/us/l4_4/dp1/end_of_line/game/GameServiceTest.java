package us.l4_4.dp1.end_of_line.game;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.Color;

@SpringBootTest
@AutoConfigureTestDatabase
public class GameServiceTest {

    @Autowired
    private GameService gs;


    @Test
    public void shouldFindAllGames() {
        List<Game> allGames = this.gs.getAllGames();
        assertNotEquals(0, allGames.size());
    }

    @Test
    public void shouldCreateGame() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setRounds(0);
        gameDTO.setStartedAt(Date.valueOf("2021-01-01"));
        gameDTO.setEndedAt(null);
        gameDTO.setWinner_id(null);
        gameDTO.setMessage_id(null);
        gameDTO.setEffect_id(null);
        gameDTO.setGamePlayers_ids(List.of(3,4));
        Game game = gs.createGame(gameDTO);
        assertNotEquals(null, game);
    }

    @Test
    public void shouldCreateNewGame() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setRounds(0);
        gameDTO.setStartedAt(Date.valueOf("2021-01-01"));
        gameDTO.setEndedAt(null);
        gameDTO.setWinner_id(null);
        gameDTO.setMessage_id(null);
        gameDTO.setEffect_id(null);
        gameDTO.setGamePlayers_ids(List.of(3,4));
        Integer player1_id = 3;
        Integer player2_id = 4;
        Color player1_color = Color.RED;
        Color player2_color = Color.RED;
        Game game = gs.createNewGame(gameDTO, player1_id, player2_id, player1_color, player2_color);
        assertNotEquals(null, game);
    }
        
}
