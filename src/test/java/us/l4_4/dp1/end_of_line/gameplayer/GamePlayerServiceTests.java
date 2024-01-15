package us.l4_4.dp1.end_of_line.gameplayer;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase
public class GamePlayerServiceTests {

    @Autowired
    private GamePlayerService gamePlayerService;

    private GamePlayer gamePlayer;

    private GamePlayer createGamePlayer(){
        GamePlayerDTO gamePlayer = new GamePlayerDTO();
        gamePlayer.setColor(Color.VIOLET);
        gamePlayer.setEnergy(2);
        gamePlayer.setPlayer_id(5);
        List<Integer> cards_ids = new ArrayList<>();
        gamePlayer.setCards_ids(cards_ids);
        return gamePlayerService.create(gamePlayer);
    }

    @Test
    void shouldCreateGamePlayer(){
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        gamePlayerDTO.setColor(Color.VIOLET);
        gamePlayerDTO.setEnergy(3);
        gamePlayerDTO.setPlayer_id(5);
        List<Integer> cards_ids = List.of(1, 2, 3);
        gamePlayerDTO.setCards_ids(cards_ids);

        GamePlayer gamePlayer = gamePlayerService.create(gamePlayerDTO);
        assertEquals(Color.VIOLET, gamePlayer.getColor());
        assertEquals(3, gamePlayer.getEnergy());
        assertEquals(5, gamePlayer.getPlayer().getId());
        assertEquals(3, gamePlayer.getCards().size());
    }

    @Test
    void shouldNotCreateGamePlayer(){
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        gamePlayerDTO.setColor(Color.VIOLET);
        gamePlayerDTO.setEnergy(3);
        gamePlayerDTO.setPlayer_id(1000);
        List<Integer> cards_ids = List.of(1, 2, 3);
        gamePlayerDTO.setCards_ids(cards_ids);

        assertThrows(NoSuchElementException.class, () -> gamePlayerService.create(gamePlayerDTO));
    }


    @Test
    void shouldFindGamePlayerById(){
        GamePlayer gamePlayer = gamePlayerService.findById(1);
        assertEquals(5, gamePlayer.getPlayer().getId());
        assertEquals(Color.VIOLET, gamePlayer.getColor());
        assertEquals(3, gamePlayer.getEnergy());
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
        assertEquals(Color.VIOLET, gamePlayers.get(0).getColor());
        assertEquals(Color.ORANGE, gamePlayers.get(1).getColor());
        assertEquals(3, gamePlayers.get(0).getEnergy());
        assertEquals(2, gamePlayers.get(1).getEnergy());


    }

    @Test
    void shouldNotFindGamePlayersByGameId(){
        assertThrows(ResourceNotFoundException.class, () -> gamePlayerService.findGamePlayersByGameId(1000));
    }

    @Test
    void shouldUpdateGamePlayer(){
        gamePlayer = createGamePlayer();
        assertEquals(Color.VIOLET, gamePlayer.getColor());
        assertEquals(2, gamePlayer.getEnergy());
        assertEquals(5, gamePlayer.getPlayer().getId());
        assertEquals(0, gamePlayer.getCards().size());

        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        gamePlayerDTO.setColor(Color.YELLOW);
        gamePlayerDTO.setEnergy(1);
        gamePlayerDTO.setPlayer_id(6);
        List<Integer> cards_ids = List.of(4, 5, 6);
        gamePlayerDTO.setCards_ids(cards_ids);

        gamePlayer = gamePlayerService.update(gamePlayer.getId(), gamePlayerDTO);
        assertEquals(Color.YELLOW, gamePlayer.getColor());
        assertEquals(1, gamePlayer.getEnergy());
        assertEquals(6, gamePlayer.getPlayer().getId());
        assertEquals(3, gamePlayer.getCards().size());
    }

    @Test
    void shouldNotUpdateGamePlayer(){
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        gamePlayerDTO.setColor(Color.YELLOW);
        gamePlayerDTO.setEnergy(1);
        gamePlayerDTO.setPlayer_id(6);
        List<Integer> cards_ids = List.of(4, 5, 6);
        gamePlayerDTO.setCards_ids(cards_ids);

        assertThrows(ResourceNotFoundException.class, () -> gamePlayerService.update( 1000, gamePlayerDTO));
    }

    @Test
    void shouldFindGamePlayerByGameAndPlayer(){
        GamePlayer gamePlayer = gamePlayerService.findGamePlayerByGameAndPlayer(5, 1);
        assertEquals(5, gamePlayer.getPlayer().getId());
        assertEquals(Color.VIOLET, gamePlayer.getColor());
        assertEquals(3, gamePlayer.getEnergy());
    }

    @Test
    void shouldNotFindGamePlayerByGameAndPlayer(){
        assertThrows(ResourceNotFoundException.class, () -> gamePlayerService.findGamePlayerByGameAndPlayer(1000, 1000));
    }
}
