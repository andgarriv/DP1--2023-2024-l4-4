package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class GamePlayerService {

    private GamePlayerRepository gamePlayerRepository;
    private PlayerRepository playerRepository;
    private CardRepository cardRepository;

    @Autowired
    public GamePlayerService(GamePlayerRepository gamePlayerRepository, PlayerRepository playerRepository,
            CardRepository cardRepository) {
        this.gamePlayerRepository = gamePlayerRepository;
        this.playerRepository = playerRepository;
        this.cardRepository = cardRepository;
    }

    public GamePlayer createGamePlayer(GamePlayerDTO newGamePlayerDTO) throws DataAccessException{
        GamePlayer gamePlayer = new GamePlayer();

        gamePlayer.setColor(newGamePlayerDTO.getColor());

        gamePlayer.setEnergy(newGamePlayerDTO.getEnergy());

        Player player = playerRepository.findById(newGamePlayerDTO.getPlayer_id()).orElseThrow();
        gamePlayer.setPlayer(player);

        List<Card> cards = newGamePlayerDTO.getCards_ids()
                .stream()
                .map(cardId -> cardRepository.findById(cardId)
                        .orElseThrow())
                .collect(Collectors.toList());
        gamePlayer.setCards(cards);

        gamePlayerRepository.save(gamePlayer);
        return gamePlayer;
    }

    public List<GamePlayer> getGamePlayersByGameId(int gameId) {
        return gamePlayerRepository.findGamePlayersByGameId(gameId);
    }

    public GamePlayer getGamePlayerById(int id) throws DataAccessException{
        return gamePlayerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", id));
    }

    public GamePlayer updateGamePlayer(GamePlayerDTO newGamePlayerDTO, int id) throws DataAccessException{
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", id));
        gamePlayer.setColor(newGamePlayerDTO.getColor());
        gamePlayer.setEnergy(newGamePlayerDTO.getEnergy());
        Player player = playerRepository.findById(newGamePlayerDTO.getPlayer_id()).orElseThrow();
        gamePlayer.setPlayer(player);
        List<Card> cards = newGamePlayerDTO.getCards_ids()
                .stream()
                .map(cardId -> cardRepository.findById(cardId)
                        .orElseThrow())
                .collect(Collectors.toList());
        gamePlayer.setCards(cards);
        gamePlayerRepository.save(gamePlayer);
        return gamePlayer;
    }
}