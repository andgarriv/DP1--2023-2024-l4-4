package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public GamePlayer create(GamePlayerDTO gamePlayerDTO) throws DataAccessException{
        GamePlayer gamePlayer = new GamePlayer();

        gamePlayer.setColor(gamePlayerDTO.getColor());

        gamePlayer.setEnergy(gamePlayerDTO.getEnergy());

        Player player = playerRepository.findById(gamePlayerDTO.getPlayer_id()).orElseThrow();
        gamePlayer.setPlayer(player);

        List<Card> cards = gamePlayerDTO.getCards_ids()
                .stream()
                .map(cardId -> cardRepository.findById(cardId)
                        .orElseThrow())
                .collect(Collectors.toList());
        gamePlayer.setCards(cards);

        gamePlayerRepository.save(gamePlayer);
        return gamePlayer;
    }

    public List<GamePlayer> findGamePlayersByGameId(Integer id) throws DataAccessException{
        List<GamePlayer> gamePlayers = gamePlayerRepository.findGamePlayersByGameId(id);
        if(gamePlayers.isEmpty() || gamePlayers == null)
            throw new ResourceNotFoundException("GamePlayer", "game_id", id);
        return gamePlayers;
    }

    public GamePlayer findById(Integer id) throws DataAccessException{
        return gamePlayerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", id));
    }

    public GamePlayer update(Integer id, GamePlayerDTO gamePlayerDTO) throws DataAccessException{
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GamePlayer", "id", id));
        gamePlayer.setColor(gamePlayerDTO.getColor());
        gamePlayer.setEnergy(gamePlayerDTO.getEnergy());
        Player player = playerRepository.findById(gamePlayerDTO.getPlayer_id()).orElseThrow();
        gamePlayer.setPlayer(player);
        List<Card> cards = gamePlayerDTO.getCards_ids()
                .stream()
                .map(cardId -> cardRepository.findById(cardId)
                        .orElseThrow())
                .collect(Collectors.toList());
        gamePlayer.setCards(cards);
        gamePlayerRepository.save(gamePlayer);
        return gamePlayer;
    }

    @Transactional
    public GamePlayer findGamePlayerByGameAndPlayer(Integer playerId, Integer gameId) throws DataAccessException{
        GamePlayer gamePlayer = gamePlayerRepository.findGamePlayerByGameAndPlayer(playerId, gameId);
        if(gamePlayer == null)
            throw new ResourceNotFoundException("GamePlayer", "game_id", gameId);
    return gamePlayer;
    }
}