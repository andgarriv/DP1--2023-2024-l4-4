package us.l4_4.dp1.end_of_line.player;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.authority.authority = 'PLAYER'")
    List<Player> findAllPlayers();

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.nickname = ?1")
    public Boolean existsByNickname(String nickname);

    @Query("SELECT p FROM Player p WHERE p.nickname = :nickname")
    Optional<Player> findByNickname(String nickname);

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.id = ?1 AND p.authority.authority = 'PLAYER'")
    public Boolean existsPlayerById(Integer id);

    @Query("SELECT COUNT(*) > 0 FROM Player p WHERE p.email = ?1")
    public Boolean existsByEmail(String email);

    @Query("SELECT p FROM Player p WHERE p.authority.authority = :auth")
	List<Player> findAllByAuthority(String auth);
}
