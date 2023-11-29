package us.l4_4.dp1.end_of_line.gameplayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GamePlayerRepository extends CrudRepository<GamePlayer, Integer>{

}
