package us.l4_4.dp1.end_of_line.player;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer>{

    @Query("SELECT f FROM Friendship f WHERE f.sender = ?1 OR f.receiver = ?1")
    List<Friendship> findAllFriendshipsByPlayerId(Integer id);
    
    @Query("SELECT f FROM Friendship f WHERE (f.sender = ?1 AND f.receiver = ?2) OR (f.sender = ?2 AND f.receiver = ?1)")
    Optional<Friendship> findFriendshipBySenderAndReceiver(Integer sender_id, Integer receiver_id);
}
