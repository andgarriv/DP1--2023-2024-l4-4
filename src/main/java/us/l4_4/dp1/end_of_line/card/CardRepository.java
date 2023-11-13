package us.l4_4.dp1.end_of_line.card;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
    
}
