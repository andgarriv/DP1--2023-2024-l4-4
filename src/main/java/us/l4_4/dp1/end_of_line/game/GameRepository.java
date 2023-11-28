package us.l4_4.dp1.end_of_line.game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>{

}
