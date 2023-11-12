package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer>{
    
    @Query("SELECT g FROM Game g")
    List<Game> findAll();

    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    List<Game> findById(int id);

    //TODO: cuando se termine el uml pensar la query
    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    List<Game> findByPlayerId(int id);

    Game save(Game game);
}
