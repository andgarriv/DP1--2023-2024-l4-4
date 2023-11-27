package us.l4_4.dp1.end_of_line.effect;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EffectRepository extends CrudRepository<Effect, Integer>{

    Effect save(Effect effect);
    void deleteById (int id);

    @Query("SELECT e FROM Effect e WHERE e.id = ?1")
    public Effect findEffectById(int id);

    @Query("SELECT e FROM Effect e")
    public  List<Effect> findAll();
    
} 