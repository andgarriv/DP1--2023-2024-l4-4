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
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Card> findAll(){
        return cardService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Card findById(@PathVariable Integer id){
        return cardService.findById(id);
    }

    @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> findAllCardsByColor(@PathVariable String color){
        List<Card> cards = cardService.findAllCardsByColor(color);
        if(cards == null)
            throw new ResourceNotFoundException(color);
        return cards;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Card create(@RequestBody @Valid CardDTO cardDTO ){
        Card card = new Card();
        card.setId(cardDTO.getId());
        card.setInitiative(cardDTO.getInitiative());
        card.setExit(Exit.valueOf(cardDTO.getExit()));
        card.setRow(cardDTO.getCard_row());
        card.setColumn(cardDTO.getCard_column());
        card.setOrientation(Orientation.valueOf(cardDTO.getOrientation()));
        card.setCardState(CardStatus.valueOf(cardDTO.getCard_statu()));
        return cardService.save(card);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Card update(@PathVariable Integer id, @RequestBody @Valid CardDTO cardDTO ){
        Card card = cardService.findById(id);
        if(card == null)
            throw new ResourceNotFoundException("Cards not found");
        card.setInitiative(cardDTO.getInitiative());
        card.setExit(Exit.valueOf(cardDTO.getExit()));
        card.setRow(cardDTO.getCard_row());
        card.setColumn(cardDTO.getCard_column());
        card.setOrientation(Orientation.valueOf(cardDTO.getOrientation()));
        card.setCardState(CardStatus.valueOf(cardDTO.getCard_statu()));
        return cardService.save(card);
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> findAllCardsOfGame(@PathVariable Integer id){
        List<Card> cards = cardService.findAllCardsOfGame(id);
        if(cards.isEmpty())
            throw new ResourceNotFoundException("No cards found for game with id: " + id);
        return cards;
    }
}
