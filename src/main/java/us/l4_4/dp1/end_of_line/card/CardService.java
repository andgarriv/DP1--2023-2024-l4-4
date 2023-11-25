package us.l4_4.dp1.end_of_line.card;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.statistic.Achievement;

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
    public Card getCardById(Integer id){


        Optional<Card> result=cardRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

     @Transactional(readOnly = true)
    public List<Card> getAll(){

        return cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void deleteCardById(Integer id){
            cardRepository.deleteById(id);
    }


    
    @Transactional(readOnly = true)
    public Card saveCard(@Valid Card c ){

        return cardRepository.save(c);
    }

    


    
}
