package us.l4_4.dp1.end_of_line.game;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.effect.EffectRepository;
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

    @Transactional(readOnly = true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }
    
}
