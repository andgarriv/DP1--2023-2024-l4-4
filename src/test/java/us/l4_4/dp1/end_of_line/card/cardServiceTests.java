package us.l4_4.dp1.end_of_line.card;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Orientation;
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
        assertThrows(ResourceNotFoundException.class, () -> cardService.findById(100000));
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


    @Test
    void shouldDeleteCard(){
        Card card = new Card();
        card.setInitiative(1);
        card.setColor(Color.YELLOW);
        card.setExit(Exit.EXIT_001_A);
        card.setIsTemplate(false);
        card.setOrientation(Orientation.S);
        Card cartaCreada = cardService.save(card);
        cardService.delete(cartaCreada.getId());
        assertThrows(ResourceNotFoundException.class, () -> cardService.findById(cartaCreada.getId()));
     } 

    @Test
    void shouldCreateCard(){
        Card card = new Card();
        card.setInitiative(1);
        card.setColor(Color.YELLOW);
        card.setExit(Exit.EXIT_001_A);
        card.setIsTemplate(false);
        card.setOrientation(Orientation.S);
        card.setCardState(CardStatus.IN_HAND);
        Card cartaCreada = cardService.save(card);

        assertEquals(1,cartaCreada.getInitiative());
        assertEquals(Color.YELLOW,cartaCreada.getColor());
        assertEquals(Exit.EXIT_001_A,cartaCreada.getExit());
        assertEquals(false,cartaCreada.getIsTemplate());
        assertEquals(Orientation.S,cartaCreada.getOrientation());
        assertEquals(CardStatus.IN_HAND, cartaCreada.getCardState());
    }
    @Test
    void shouldUpdateCard(){
        
        Card card = new Card();
        card.setInitiative(1);
        card.setColor(Color.YELLOW);
        card.setExit(Exit.EXIT_001_A);
        card.setIsTemplate(false);
        card.setOrientation(Orientation.S);
        Card cartaCreada = cardService.save(card);
        CardDTO dto = new CardDTO();
        dto.setColumn(4);
        dto.setRow(3);
        dto.setOrientation("N");

        Card cartaActualizada = cardService.update(cartaCreada.getId(), dto);
        assertEquals(3, cartaActualizada.getRow());
        assertEquals(4, cartaActualizada.getColumn());
        assertEquals(Orientation.N, cartaActualizada.getOrientation());
    }
}