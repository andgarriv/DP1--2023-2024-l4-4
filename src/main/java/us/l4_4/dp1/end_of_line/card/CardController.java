package us.l4_4.dp1.end_of_line.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @GetMapping("/{color}")
    public List<Card> findAllColorcard(@PathVariable("color") String color){
        List<Card> cards = cardService.getAllColorCards(color);

        if(cards == null){
            throw new ResourceNotFoundException(color);
        }
        return cards;
    }

    
}
