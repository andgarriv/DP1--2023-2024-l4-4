package us.l4_4.dp1.end_of_line.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.effect.EffectRepository;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerRepository;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageRepository;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class GameService {

    GameRepository gameRepository;
    PlayerRepository playerRepository;
    GamePlayerRepository gamePlayerRepository;
    MessageRepository messageRepository;
    EffectRepository effectRepository;
    CardRepository cardRepository;
    CardService cardService;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, MessageRepository messageRepository, EffectRepository effectRepository, GamePlayerRepository gamePlayerRepository, CardRepository cardRepository){
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.messageRepository = messageRepository;
        this.effectRepository = effectRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.cardRepository = cardRepository;
    }
    
    @Transactional
    public Game save(Game game){
        return gameRepository.save(game);
    }

    @Transactional
    public Game createGame(GameDTO gameDTO){
        Game game = new Game();
        game.setRounds(gameDTO.getRounds());
        if(gameDTO.getWinner_id() != null){
            game.setWinner(playerRepository.findById(gameDTO.getWinner_id()).get());
        }
        game.setStartedAt(gameDTO.getStartedAt());
        if(gameDTO.getEndedAt() != null){
            game.setEndedAt(gameDTO.getEndedAt());
        }
        if(gameDTO.getMessage_id() != null){
            List<Message> messages = gameDTO.getMessage_id()
                .stream()
                .map(messageId -> messageRepository.findMessageById(messageId))
                .collect(Collectors.toList());
            game.setMessage(messages);
        }
        if(gameDTO.getEffect_id() != null){
            game.setEffect(effectRepository.findById(gameDTO.getEffect_id()).get());
        }
        List<GamePlayer> gamePlayers = gameDTO.getGamePlayers_ids()
            .stream()
            .map(gamePlayerId -> gamePlayerRepository.findById(gamePlayerId).get())
            .collect(Collectors.toList());
        game.setGamePlayers(gamePlayers);
        
        List<Card> cards = gamePlayers.stream()
            .map(gamePlayer -> gamePlayer.getCards())
            .flatMap(cardsList -> cardsList.stream())
            .collect(Collectors.toList());
        game.setCards(cards);
        
        return gameRepository.save(game);
    }

    @Transactional
    public Game createNewGame(GameDTO gameDTO, Integer playerID1, Integer playerID2, Color c1, Color c2){
        Game game = new Game();
        game.setRounds(0);
        game.setWinner(null);
        game.setStartedAt(Date.from(java.time.Instant.now()));
        game.setEndedAt(null);
        game.setMessage(null);
        game.setEffect(null);
        GamePlayer p1 = new GamePlayer();
        p1.setColor(c1);
        p1.setEnergy(3);
        p1.setPlayer(playerRepository.findById(playerID1).get());
        // Duplicar las cartas del color c1 y asignarlas a p1
        List<Card> cardsC1 = cardRepository.findCardsByColor(c1);
        List<Card> cardsC1Duplicated = new ArrayList<>();
        for(Card card : cardsC1){
            Card newCard = new Card();
            newCard.setColumn(null);
            newCard.setRow(null);
            newCard.setIniciative(card.getIniciative());
            newCard.setCard_Status(CardStatus.MANO);
            newCard.setColor(card.getColor());
            newCard.setExit(card.getExit());
            newCard.setOrientation(card.getOrientation());
            cardsC1Duplicated.add(newCard);
            cardRepository.save(newCard);
        }
        p1.setCards(cardsC1Duplicated);

        GamePlayer p2 = new GamePlayer();
        p2.setColor(c2);
        p2.setEnergy(3);
        p2.setPlayer(playerRepository.findById(playerID2).get());
        // Duplicar las cartas del color c2 y asignarlas a p2
        List<Card> cardsC2 = cardRepository.findCardsByColor(c2);
        List<Card> cardsC2Duplicated = new ArrayList<>();
        for(Card card : cardsC2){
            Card newCard = new Card();
            newCard.setColumn(null);
            newCard.setRow(null);
            newCard.setIniciative(card.getIniciative());
            newCard.setCard_Status(CardStatus.MANO);
            newCard.setColor(card.getColor());
            newCard.setExit(card.getExit());
            newCard.setOrientation(card.getOrientation());
            cardsC2Duplicated.add(newCard);
            cardRepository.save(newCard);
        }
        p2.setCards(cardsC2Duplicated);
        gamePlayerRepository.save(p1);
        gamePlayerRepository.save(p2);
        List<GamePlayer> gamePlayers = List.of(p1,p2);
        game.setGamePlayers(gamePlayers);
        
        List<Card> cards = gamePlayers.stream()
            .map(gamePlayer -> gamePlayer.getCards())
            .flatMap(cardsList -> cardsList.stream())
            .collect(Collectors.toList());
        game.setCards(cards);
        gameRepository.save(game);
        return game;
    }

    @Transactional(readOnly = true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }
    
}
