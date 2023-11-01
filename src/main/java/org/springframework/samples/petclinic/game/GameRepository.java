import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;

public interface GameRepository extends CrudRepository<Game, Integer> {
    
    @Query("SELECT p FROM Player p WHERE p.game.player.id = :playerId")
    List<Player> findPlayersOfGame(int playerId);

}