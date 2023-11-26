package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import us.l4_4.dp1.end_of_line.card.Card;
import us.l4_4.dp1.end_of_line.card.CardRepository;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class GamePlayerService {

    private GamePlayerRepository gamePlayerRepository;
    private PlayerRepository PlayerRepository;
    private CardRepository cardRepository;

    public GamePlayerService(GamePlayerRepository gamePlayerRepository, PlayerRepository PlayerRepository,
            CardRepository cardRepository) {
        this.gamePlayerRepository = gamePlayerRepository;
        this.PlayerRepository = PlayerRepository;
        this.cardRepository = cardRepository;
    }

    public GamePlayer createGamePlayer(GamePlayerDTO newGamePlayerDTO) {
        GamePlayer gamePlayer = new GamePlayer();

        gamePlayer.setColor(newGamePlayerDTO.getColor());

        gamePlayer.setEnergy(newGamePlayerDTO.getEnergy());

        Player player = PlayerRepository.findById(newGamePlayerDTO.getPlayer_id()).orElseThrow();
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

    public GamePlayer getGamePlayerById(int id) {
        return gamePlayerRepository.findById(id);
    }

    public GamePlayer updateGamePlayer(GamePlayerDTO newGamePlayerDTO, int id) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(id);
        gamePlayer.setColor(newGamePlayerDTO.getColor());
        gamePlayer.setEnergy(newGamePlayerDTO.getEnergy());
        Player player = PlayerRepository.findById(newGamePlayerDTO.getPlayer_id()).orElseThrow();
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
