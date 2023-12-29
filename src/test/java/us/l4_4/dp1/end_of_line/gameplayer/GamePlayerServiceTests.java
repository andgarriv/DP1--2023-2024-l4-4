package us.l4_4.dp1.end_of_line.gameplayer;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@SpringBootTest
public class GamePlayerServiceTests {

    @Autowired
    private GamePlayerService gamePlayerService;

    /* private GamePlayer createGamePlayer(){
        Player player = new Player();
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setId(1);
        gamePlayer.setEnergy(3);
        gamePlayer.setPlayer(player);
        gamePlayer.setColor(Color.RED);
        gamePlayer.setCards();
        return gamePlayer;
    } */

    @Test
    void shouldFindGamePlayerById(){
        GamePlayer gamePlayer = gamePlayerService.findById(1);
        assertEquals(5, gamePlayer.getPlayer().getId());
        assertEquals(Color.VIOLET, gamePlayer.getColor());
    }

    @Test
    void shouldNotFindGamePlayerById(){
        assertThrows(ResourceNotFoundException.class, () -> gamePlayerService.findById(1000));
    }

    @Test
    void shouldFindGamePlayersByGameId(){
        List<GamePlayer> gamePlayers = gamePlayerService.findGamePlayersByGameId(1);
        assertEquals(2, gamePlayers.size());
        assertEquals(5, gamePlayers.get(0).getPlayer().getId());
        assertEquals(6, gamePlayers.get(1).getPlayer().getId());
    }

    @Test
    void shouldNotFindGamePlayersByGameId(){
        assertThrows(ResourceNotFoundException.class, () -> gamePlayerService.findGamePlayersByGameId(1000));
    }

    @Test
    void shouldInsertGamePlayer(){

    }


}
