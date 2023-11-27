package us.l4_4.dp1.end_of_line.gameplayer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface GamePlayerRepository extends CrudRepository<GamePlayer, Integer>{

    @Query("SELECT gp FROM GamePlayer gp WHERE gp.id = ?1")
    GamePlayer findById(int id);

    // @Query("SELECT gp FROM GamePlayer gp WHERE gp.game.id = ?1")
    // List<GamePlayer> findByGameId(int gameId);




    


    
}
