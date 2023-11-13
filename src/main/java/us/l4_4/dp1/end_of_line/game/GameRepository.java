package us.l4_4.dp1.end_of_line.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>{
    
    @Query("SELECT g FROM Game g")
    List<Game> findAll();

    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    List<Game> findById(int id);

    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    List<Game> findByPlayerId(int id);

    Game save(Game game);
}
