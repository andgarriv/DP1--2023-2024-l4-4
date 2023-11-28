package us.l4_4.dp1.end_of_line.player;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.nickname = ?1")
    public Boolean existsByNickname(String nickname);

    @Query("SELECT p FROM Player p WHERE p.nickname = :nickname")
    Optional<Player> findByNickname(String nickname);

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.email = ?1")
    public Boolean existsByEmail(String email);

    @Query("SELECT p FROM Player p WHERE p.authority.authority = :auth")
	List<Player> findAllByAuthority(String auth);
    
    @Modifying
    @Query("DELETE FROM Player p WHERE p.id = :id")
    public void deletePlayer(@Param("id")Integer id);

    @Query("SELECT p FROM Player p WHERE p.id <> :id")
    List<Player> findAllExceptWithId(@Param("id") Integer id);
}
