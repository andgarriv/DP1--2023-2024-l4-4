package us.l4_4.dp1.end_of_line.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.card.CardService;
import us.l4_4.dp1.end_of_line.effect.EffectRepository;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.exceptions.BadRequestException;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
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

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository,
            MessageRepository messageRepository, EffectRepository effectRepository,
            GamePlayerRepository gamePlayerRepository, CardRepository cardRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.messageRepository = messageRepository;
        this.effectRepository = effectRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Game save(Game game) throws DataAccessException {
        return gameRepository.save(game);
    }

    @Transactional
    public Game createGame(GameDTO gameDTO) throws DataAccessException {
        Game game = new Game();
        game.setRounds(gameDTO.getRounds());
        if (gameDTO.getWinner_id() != null) {
            game.setWinner(playerRepository.findById(gameDTO.getWinner_id()).get());
        }
        game.setStartedAt(gameDTO.getStartedAt());
        if (gameDTO.getEndedAt() != null) {
            game.setEndedAt(gameDTO.getEndedAt());
        }
        if (gameDTO.getMessage_id() != null) {
            List<Message> messages = gameDTO.getMessage_id()
                    .stream()
                    .map(messageId -> messageRepository.findById(messageId)
                            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId)))
                    .collect(Collectors.toList());
            game.setMessage(messages);
        }
        if (gameDTO.getEffect_id() != null) {
            game.setEffect(effectRepository.findById(gameDTO.getEffect_id()).get());
        }
        List<GamePlayer> gamePlayers = gameDTO.getGamePlayers_ids()
                .stream()
                .map(gamePlayerId -> gamePlayerRepository.findById(gamePlayerId).get())
                .collect(Collectors.toList());
        game.setGamePlayers(gamePlayers);

        return gameRepository.save(game);
    }

    @Transactional
    public Game createNewGame(Integer playerID1, Integer playerID2, Color c1, Color c2) throws DataAccessException {

        if (checkOnlyOneGameForEachPlayer(playerID1)) {
            throw new BadRequestException("No se puede crear una partida ya que el jugador tiene una en curso");
        }
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
        List<Card> cardsC1 = cardRepository.findTemplatedCardsByColor(c1);
        List<Card> cardsC1Duplicated = new ArrayList<>();
        for (Card card : cardsC1) {
            Card newCard = new Card();
            if (card.getExit() == Exit.START){
                newCard.setColumn(2);
                newCard.setRow(3);
                newCard.setCard_Status(CardStatus.ON_BOARD);
            }else{
                newCard.setColumn(null);
                newCard.setRow(null);
                newCard.setCard_Status(CardStatus.IN_DECK);
            }
            newCard.setIniciative(card.getIniciative());
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
        List<Card> cardsC2 = cardRepository.findTemplatedCardsByColor(c2);
        List<Card> cardsC2Duplicated = new ArrayList<>();
        for (Card card : cardsC2) {
            Card newCard = new Card();
            if (card.getExit() == Exit.START){
                newCard.setColumn(4);
                newCard.setRow(3);
                newCard.setCard_Status(CardStatus.ON_BOARD);
            }else{
                newCard.setColumn(null);
                newCard.setRow(null);
                newCard.setCard_Status(CardStatus.IN_DECK);
            }
            newCard.setIniciative(card.getIniciative());
            newCard.setColor(card.getColor());
            newCard.setExit(card.getExit());
            newCard.setOrientation(card.getOrientation());
            cardsC2Duplicated.add(newCard);
            cardRepository.save(newCard);
        }
        p2.setCards(cardsC2Duplicated);
        gamePlayerRepository.save(p1);
        gamePlayerRepository.save(p2);
        List<GamePlayer> gamePlayers = List.of(p1, p2);
        game.setGamePlayers(gamePlayers);
        gameRepository.save(game);
        return game;
    }

    @Transactional(readOnly = true)
    public Iterable<Game> getAllGames() throws DataAccessException {
        return gameRepository.findAll();
    }

    private Boolean checkOnlyOneGameForEachPlayer(Integer id1) {
        Boolean res = false;
        if (!gameRepository.findNotEndedGamesByPlayerId(id1).isEmpty()) {
            res = true;
        }
        return res;
    }

    public List<Card> getFiveRandomCards(Integer gamePlayerId) {
        if (gamePlayerRepository.findById(gamePlayerId) == null) {
            throw new ResourceNotFoundException("GamePlayer", "id", gamePlayerId);
        }
        List<Card> cards = gamePlayerRepository.findById(gamePlayerId).get().getCards();
        // Mezclar la lista de cartas
        Collections.shuffle(cards);
        // Tomar las primeras cinco cartas
        List<Card> randomCards = cards.subList(0, Math.min(5, cards.size()));
        for(Card card : randomCards) {
            card.setCard_Status(CardStatus.IN_HAND);
            cardRepository.save(card);
        }

        return randomCards;
    }

    public List<Card> getNeedCardsToGetFive(Integer gamePlayerId){
        if (gamePlayerRepository.findById(gamePlayerId) == null) {
            throw new ResourceNotFoundException("GamePlayer", "id", gamePlayerId);
        }
        List<Card> cards = gamePlayerRepository.findById(gamePlayerId).get().getCards();

        List<Card> cardsInDeck = cards.stream()
                .filter(card -> card.getCard_Status() == CardStatus.IN_DECK)
                .collect(Collectors.toList());
        List<Card> cardsInHand = cards.stream()
                .filter(card -> card.getCard_Status() == CardStatus.IN_HAND)
                .collect(Collectors.toList());
        
         Collections.shuffle(cardsInDeck);
         List<Card> randomCards = cardsInDeck.subList(0,5-cardsInHand.size()-1);

        for(Card card : randomCards ) {
            cardsInHand.add(card);
            card.setCard_Status(CardStatus.IN_HAND);
            cardRepository.save(card);
        }
        return cardsInHand;
    }

    @Transactional
    public Game updateGame(Integer id, GameDTO gameDTO) throws DataAccessException {
        Game game = gameRepository.findById(id).get();
        game.setRounds(gameDTO.getRounds());
        if (gameDTO.getWinner_id() != null) {
            game.setWinner(playerRepository.findById(gameDTO.getWinner_id()).get());
        }
        game.setStartedAt(gameDTO.getStartedAt());
        if (gameDTO.getEndedAt() != null) {
            game.setEndedAt(gameDTO.getEndedAt());
        }
        if (gameDTO.getMessage_id() != null) {
            List<Message> messages = gameDTO.getMessage_id()
                    .stream()
                    .map(messageId -> messageRepository.findById(messageId)
                            .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId)))
                    .collect(Collectors.toList());
            game.setMessage(messages);
        }
        if (gameDTO.getEffect_id() != null) {
            game.setEffect(effectRepository.findById(gameDTO.getEffect_id()).get());
        }
        List<GamePlayer> gamePlayers = gameDTO.getGamePlayers_ids()
                .stream()
                .map(gamePlayerId -> gamePlayerRepository.findById(gamePlayerId).get())
                .collect(Collectors.toList());
        game.setGamePlayers(gamePlayers);
        return gameRepository.save(game);
    }

    private Integer whoIsNext(Integer id1, Integer id2) {
        Integer res = null;
        Integer maxSize = null;

        if (gamePlayerRepository.findById(id1) == null) {
            throw new ResourceNotFoundException("GamePlayer", "id", id1);
        } else if (gamePlayerRepository.findById(id2) == null) {
            throw new ResourceNotFoundException("GamePlayer", "id", id2);
        }
        List<Card> player1Cards = gamePlayerRepository.findById(id1).get().getCards().stream()
                .filter(card -> card.getCard_Status() == CardStatus.ON_BOARD)
                .sorted(Comparator.comparing(Card::getTimeStamp).reversed())
                .collect(Collectors.toList());

        List<Card> player2Cards = gamePlayerRepository.findById(id1).get().getCards().stream()
                .filter(card -> card.getCard_Status() == CardStatus.ON_BOARD)
                .sorted(Comparator.comparing(Card::getTimeStamp).reversed())
                .collect(Collectors.toList());

        if(player1Cards.size() < player2Cards.size())maxSize = player1Cards.size();
        else maxSize = player2Cards.size();
        
            for (int i = 0; i < maxSize; i++) {
                if (player1Cards.get(i).getIniciative() > player2Cards.get(i).getIniciative()) {
                    res = id2;
                } else if (player1Cards.get(i).getIniciative() < player2Cards.get(i).getIniciative()) {
                   res = id1;
                }
            }
            if(res == null) {
                 if(player1Cards.size() < player2Cards.size()){res= id1;}
                 else {res = id2;}
            }
        return res;
    }

    @Transactional(readOnly = true)
    public List<Game> getNotEndedGamesByPlayerId(Integer playerId) {
        return gameRepository.findNotEndedGamesByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByPlayerId(Integer playerId) {
        return gameRepository.findGamesByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public Game getGameById(Integer id) throws DataAccessException {
        return gameRepository.findById(id).get();
    }
}
