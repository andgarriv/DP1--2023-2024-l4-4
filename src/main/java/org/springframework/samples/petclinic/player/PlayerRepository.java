package org.springframework.samples.petclinic.player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p FROM Player p")
    public List<Player> findAllPlayers();

    @Query("SELECT p FROM Player p WHERE p.nickname = :nickname")
    public Player findPlayerByName(String nickname);

    @Query("DELETE FROM Player p WHERE p.nickname = : nickname")
    public void deletePlayer(String nickname);


    
    
}
