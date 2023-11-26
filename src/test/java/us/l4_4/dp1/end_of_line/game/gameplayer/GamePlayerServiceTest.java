package us.l4_4.dp1.end_of_line.game.gameplayer;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerDTO;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;

@SpringBootTest
public class GamePlayerServiceTest {

    @Autowired
    public GamePlayerService gps;


    @Test
    @Transactional
    public void shouldCreateGamePlayer(){
        GamePlayerDTO gp = new GamePlayerDTO();
        gp.setColor(Color.BLUE);
        gp.setEnergy(3);
        gp.setPlayer_id(3);
        gp.setCards_ids(List.of(1,2,3));
        gps.createGamePlayer(gp);
    }

}
