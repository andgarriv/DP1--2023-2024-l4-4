package us.l4_4.dp1.end_of_line.message;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.micrometer.core.instrument.Measurement;
import us.l4_4.dp1.end_of_line.game.Game;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{

    Message save(Message message);
    void deleteById (int id);

    @Query("SELECT m FROM Message m WHERE m.id = ?1")
    public Message findMessageById(int id);

    @Query("SELECT m FROM Message m")
    public List<Message> findAll();

    
} 
