package us.l4_4.dp1.end_of_line.game;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

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
        
}
