package us.l4_4.dp1.end_of_line.card;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;


@SpringBootTest
public class cardServiceTests {

    @Autowired
    CardService cardService;

    @Test
    void shouldFindCardById(){
        Card card = cardService.findById(1);
        assertEquals(true, card.getIsTemplate());
        assertEquals(Color.RED, card.getColor());
        assertEquals(Exit.EXIT_001_A, card.getExit());
    }

    @Test
    void shouldNotFindCardById(){
        assertThrows(ResourceNotFoundException.class, () -> cardService.findById(1000));
    }

    @Test
    void shouldFindAllCardsOfGame(){
        List<Card> cards = cardService.findAllCardsOfGame(17);
        assertEquals(50, cards.size());
    }

    @Test
    void shouldNotFindCardsOfGame(){
        assertThrows(ResourceNotFoundException.class, () -> cardService.findAllCardsOfGame(1000));
    }

    /* @Test
    @Transactional
    void shouldDeleteCard(){
        Integer initialCount = cardService.findAllCardsOfGame(17).size();
        Card card = new Card();
        card.setId(500);
        card.setInitiative(1);
        card.setColor(Color.YELLOW);
        card.setExit(Exit.EXIT_001_A);
        card.setIsTemplate(false);
        card.setOrientation(Orientation.S);
        cardService.save(card);
        Integer count = cardService.findAllCardsOfGame(17).size();
        cardService.delete(500);
        Integer finalCount = cardService.findAllCardsOfGame(17).size();
        assertEquals(initialCount, finalCount);
        assertEquals(initialCount, 50);
        assertEquals(count, finalCount + 1);
    } */
}