package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.l4_4.dp1.end_of_line.player.Player;


@Repository
public interface GamePlayerRepository extends CrudRepository<GamePlayer, Integer>{

    @Query("SELECT g.gamePlayers FROM Game g WHERE g.id = ?1")
    List<GamePlayer> findGamePlayersByGameId(Integer id);
    

    @Query("SELECT gp FROM GamePlayer gp WHERE gp.player.id = :playerId AND gp IN (SELECT g.gamePlayers FROM Game g WHERE g.id = :gameId)")
    GamePlayer findGamePlayerByGameAndPlayer(Integer playerId, Integer gameId);

    @Query("SELECT gp.player FROM GamePlayer gp WHERE gp.id = :gamePlayerId")
    Player findPlayerByGamePlayerId(Integer gamePlayerId);
}
