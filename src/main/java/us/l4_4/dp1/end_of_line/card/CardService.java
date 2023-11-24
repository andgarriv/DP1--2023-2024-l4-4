package us.l4_4.dp1.end_of_line.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
    
    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    @Transactional(readOnly = true)
    public List<Card> getAllColorCards(String color){

        return cardRepository.findCardsByColor(color);
    }

    
}
