package us.l4_4.dp1.end_of_line.message;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{

    @Query("SELECT m FROM Game g JOIN g.message m WHERE g.id = ?1")
    List<Message> findAllMessagesByGameId(Integer id);
} 
