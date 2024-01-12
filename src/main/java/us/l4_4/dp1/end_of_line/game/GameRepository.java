package us.l4_4.dp1.end_of_line.game;

import java.time.Duration;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    @Query("SELECT g FROM Game g JOIN g.gamePlayers gp WHERE gp.player.id = ?1 AND g.endedAt IS NULL")
    List<Game> findNotEndedGamesByPlayerId(int playerId);

    @Query("SELECT g FROM Game g JOIN g.gamePlayers gp WHERE gp.player.id = ?1")
    List<Game> findGamesByPlayerId(int playerId);

    @Query("SELECT gp FROM GamePlayer gp JOIN gp.cards c WHERE c.id = ?1")
    GamePlayer findGameplayerByCardId(int cardId);

    @Query("SELECT count(g) FROM Game g")
    Integer getTotalNumberOfGames();

    @Query("SELECT count(DISTINCT gp.player.id) FROM GamePlayer gp")
    Integer getNumberOPlayers();

    @Query("SELECT count(g) FROM Game g WHERE g.winner IS NOT NULL")
    Integer getNumberOfGamesFinished();

    @Query("SELECT max(g.round) FROM Game g")
    Integer getMaxRounds();

    @Query("SELECT min(g.round) FROM Game g")
    Integer getMinRounds();

    @Query("SELECT avg(g.round) FROM Game g")
    Double getAverageRounds();

    @Query("SELECT max(g.endedAt - g.startedAt) FROM Game g WHERE g.endedAt IS NOT NULL")
    Duration getMaxGameDuration();

    @Query("SELECT min(g.endedAt - g.startedAt) FROM Game g WHERE g.endedAt IS NOT NULL")
    Duration getMinGameDuration();

    @Query("SELECT avg(g.endedAt - g.startedAt) FROM Game g WHERE g.endedAt IS NOT NULL")
    Double getAverageGameDuration();

    @Query("SELECT SUM(g.endedAt - g.startedAt) FROM Game g WHERE g.endedAt IS NOT NULL")
    Long getTotalGameDuration();

    @Query("SELECT MAX(count) FROM (SELECT COUNT(gp) as count FROM GamePlayer gp GROUP BY gp.player.id)")
    Integer getMaxGamesPlayedByPlayer();

    @Query("SELECT MIN(count) FROM (SELECT COUNT(gp) as count FROM GamePlayer gp GROUP BY gp.player.id)")
    Integer getMinGamesPlayedByPlayer();

    @Query("SELECT avg(3 - gp.energy) FROM GamePlayer gp")
    Double getAverageEnergyUsed();

    @Query("SELECT gp.color, COUNT(gp.color) FROM GamePlayer gp GROUP BY gp.color")
    List<Object[]> getColorUsage();
}
