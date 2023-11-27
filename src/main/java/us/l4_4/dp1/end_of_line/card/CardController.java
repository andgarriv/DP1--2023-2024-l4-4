package us.l4_4.dp1.end_of_line.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("api/v1/cards")
@Tag(name= "Cards", description = "API for the management of Cards")
@SecurityRequirement(name = "bearerAuth")
public class CardController {

    private CardService cardService;

    @Autowired 
    public CardController(CardService cardService){
        this.cardService = cardService;
    }
    @GetMapping
    public List<Card> findAll(){
        List<Card> cards = cardService.getAll();
            return cards;
    }

    @GetMapping("/{id}")
    public Card findById(@PathVariable int id){
        Card cards = cardService.getCardById(id);
            return cards;
    }


    @GetMapping("/color/{color}")
    public List<Card> findAllColorcard(@PathVariable("color") String color){
        List<Card> cards = cardService.getAllColorCards(color);

        if(cards == null){
            throw new ResourceNotFoundException(color);
        }
        return cards;
    }
     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public Card createCard(@RequestBody @Valid CardDTO newCard ){
        Card c = new Card();
        c.setId(newCard.getId());
        c.setIniciative(newCard.getInitiative());
        c.setExit(Exit.valueOf(newCard.getExit()));
        c.setRow(newCard.getCard_row());
        c.setColumn(newCard.getCard_column());
        c.setOrientation(Orientation.valueOf(newCard.getOrientation()));
        c.setCard_Status(CardStatus.valueOf(newCard.getCard_statu()));
        Card res = cardService.saveCard(c);
        return res;
     }



     @PutMapping("/{id}")
     public Card updateCard(@PathVariable int id,@RequestBody @Valid CardDTO newCard ){
        Card c = cardService.getCardById(id);
        if(c == null){
            throw new ResourceNotFoundException("Cards not found");
        }

        c.setIniciative(newCard.getInitiative());
        c.setExit(Exit.valueOf(newCard.getExit()));
        c.setRow(newCard.getCard_row());
        c.setColumn(newCard.getCard_column());
        c.setOrientation(Orientation.valueOf(newCard.getOrientation()));
        c.setCard_Status(CardStatus.valueOf(newCard.getCard_statu()));
        Card res = cardService.saveCard(c);
        return res;
     }













    
}
