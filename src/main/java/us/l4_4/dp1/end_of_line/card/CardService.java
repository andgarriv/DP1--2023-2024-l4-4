package us.l4_4.dp1.end_of_line.card;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<Card> getAllColorCards(String color) throws DataAccessException{
        return cardRepository.findCardsByColor(Color.valueOf(color));
    }

    @Transactional(readOnly = true)
    public Card getCardById(Integer id) throws DataAccessException{
        Optional<Card> result=cardRepository.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional(readOnly = true)
    public Iterable<Card> getAll() throws DataAccessException{
        return cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void deleteCardById(Integer id) throws DataAccessException{
        cardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Card saveCard(@Valid Card card ) throws DataAccessException{
        return cardRepository.save(card);
    }
}
