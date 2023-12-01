package us.l4_4.dp1.end_of_line.card;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.health.HealthProperties.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{

        @Query("SELECT c FROM Card c WHERE c.color = ?1")
        List<Card> findCardsByColor(Color color);

        @Query("SELECT c FROM Card c WHERE c.color = ?1 AND c.is_Template = true")
        List<Card> findTemplatedCardsByColor(Color color);

        @Query("SELECT c FROM GamePlayer g JOIN g.cards c WHERE g.id = ?1")
        List<Card> findCardsByStatus(Status status);

        @Query("SELECT c FROM GamePlayer g JOIN g.cards c WHERE g.id = ?1")
        List<Card> findCardsByGamePlayer(Integer gamePlayerId);
}
