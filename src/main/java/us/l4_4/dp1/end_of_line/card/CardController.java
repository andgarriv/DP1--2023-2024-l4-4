package us.l4_4.dp1.end_of_line.card;

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
    
    /* @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Card> findAll(){
        return cardService.findAll();
    } */

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Card findById(@PathVariable Integer id){
        return cardService.findById(id);
    }

    /* @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> findAllCardsByColor(@PathVariable String color){
        return cardService.findAllCardsByColor(color);
    } */

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
        card.setInitiative(cardDTO.getInitiative());
        card.setExit(Exit.valueOf(cardDTO.getExit()));
        card.setRow(cardDTO.getCard_row());
        card.setColumn(cardDTO.getCard_column());
        card.setOrientation(Orientation.valueOf(cardDTO.getOrientation()));
        card.setCardState(CardStatus.valueOf(cardDTO.getCard_statu()));
        return cardService.save(card);
    }
}
