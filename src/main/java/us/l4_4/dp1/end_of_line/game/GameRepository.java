package us.l4_4.dp1.end_of_line.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>{

    @Query("SELECT g FROM Game g JOIN g.gamePlayers gp WHERE gp.player.id = ?1 AND g.endedAt IS NULL")
    List<Game> findNotEndedGamesByPlayerId(int playerId);

    @Query("SELECT g FROM Game g JOIN g.gamePlayers gp WHERE gp.player.id = ?1")
    List<Game> findGamesByPlayerId(int playerId);
    
}
