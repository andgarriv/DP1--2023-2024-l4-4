package us.l4_4.dp1.end_of_line.card;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.enums.Color;

@Service
public class CardService {
    
    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    @Transactional(readOnly = true)
    public List<Card> getAllColorCards(String color){


        return cardRepository.findCardsByColor(Color.valueOf(color));
    }

     @Transactional(readOnly = true)
    public List<Card> getAll(){

        return cardRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Card saveCard(@Valid Card c ){

        return cardRepository.save(c);
    }

    


    
}
