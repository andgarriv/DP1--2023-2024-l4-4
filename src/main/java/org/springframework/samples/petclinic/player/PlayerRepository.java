package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p FROM Player p")
    public List<Player> findAllPlayers();

    @Query("SELECT p FROM Player p WHERE p.nickname = :nickname")
    public Player findPlayerByName(String nickname);

    @Query("DELETE FROM Player p WHERE p.nickname = : nickname")
    public void deletePlayer(String nickname);

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.nickname = ?1")
    public Boolean existsByNickname(String nickname);

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.email = ?1")
    public Boolean existsByEmail(String email);
    
}
