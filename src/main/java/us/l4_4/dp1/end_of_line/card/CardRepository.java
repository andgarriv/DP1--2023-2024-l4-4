package us.l4_4.dp1.end_of_line.card;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import us.l4_4.dp1.end_of_line.enums.Color;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{

        @Query("SELECT c FROM Card c WHERE c.color = ?1 AND c.isTemplate = true")
        List<Card> findAllTemplatedCardsByColor(Color color);

        @Query("SELECT c FROM GamePlayer g JOIN g.cards c WHERE g.id = ?1")
        List<Card> findAllCardsByGamePlayer(Integer id);
}
