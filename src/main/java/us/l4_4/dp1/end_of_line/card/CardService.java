package us.l4_4.dp1.end_of_line.card;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.game.GameRepository;
import us.l4_4.dp1.end_of_line.game.GameService;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;

@Service
public class CardService {
    
    CardRepository cardRepository;
    GameService gameService;

    @Autowired
    public CardService(CardRepository cardRepository, GameService gameService ){
        this.cardRepository = cardRepository;
        this.gameService = gameService;
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

    @Transactional
    public void deleteCardById(Integer id) throws DataAccessException{
        cardRepository.deleteById(id);
    }

    @Transactional
    public Card saveCard(@Valid Card card ) throws DataAccessException{
        return cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public List<Card> getCardsOfGame(Integer gameId) throws DataAccessException{
        List<GamePlayer> gamePlayers = gameService.getGameById(gameId).getGamePlayers();
        Integer gamePlayerId1 = gamePlayers.get(0).getId();
        Integer gamePlayerId2 = gamePlayers.get(1).getId();
        List<Card> cards = cardRepository.findCardsByGamePlayer(gamePlayerId1);
        cards.addAll(cardRepository.findCardsByGamePlayer(gamePlayerId2));
        return cards;
    }
}
