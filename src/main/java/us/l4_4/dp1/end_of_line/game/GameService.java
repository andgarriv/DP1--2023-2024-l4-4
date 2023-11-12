package us.l4_4.dp1.end_of_line.game;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }
    
    @Transactional
    public Game save(Game game){
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByPlayerId(int id){
        return gameRepository.findByPlayerId(id);
    }
}
