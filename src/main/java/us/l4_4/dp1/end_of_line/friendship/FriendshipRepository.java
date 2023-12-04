package us.l4_4.dp1.end_of_line.friendship;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer>{

    @Query("SELECT f FROM Friendship f WHERE f.sender.id = ?1 OR f.receiver.id = ?1")
    Iterable<Friendship> findAllFriendshipsByPlayerId(Integer id);
    
    @Query("SELECT f FROM Friendship f WHERE (f.sender.id = ?1 AND f.receiver.id = ?2) OR (f.sender.id = ?2 AND f.receiver.id = ?1)")
    Optional<Friendship> findFriendshipBySenderAndReceiver(Integer sender_id, Integer receiver_id);
}
