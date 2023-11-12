package us.l4_4.dp1.end_of_line.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    
    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }
    
}
