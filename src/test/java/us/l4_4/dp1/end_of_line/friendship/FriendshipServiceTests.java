package us.l4_4.dp1.end_of_line.friendship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase
class FriendshipServiceTests {

    @Autowired
    private FriendshipService friendshipService;

    private Friendship createFriendship(){
        FriendshipDTO friendshipDTO = new FriendshipDTO();
        friendshipDTO.setSender(4);
        friendshipDTO.setReceiver(6);
        return this.friendshipService.create(friendshipDTO);
    }

    @Test
    void shouldFindFriendshipById() {
        Friendship friendship = friendshipService.findById(1);
        assert(friendship.getSender().getId() == 3);
        assert(friendship.getReceiver().getId() == 4);
        assert(friendship.getFriendState().equals(FriendStatus.ACCEPTED));
    }

    @Test
    void shouldNotFindFriendshipByBadId(){
        assertThrows(ResourceNotFoundException.class, () -> friendshipService.findById(1000));
    }

    @Test
    void shouldFindAllFriendships(){
        Iterable<Friendship> friendships = friendshipService.findAll();
        long count = StreamSupport.stream(friendships.spliterator(), false).count();
        assertEquals(16, count);
    }

    @Test
    void shouldFindAllFriendshipsByPlayerId(){
        Iterable<Friendship> friendships = friendshipService.findAllFriendshipsByPlayerId(3, FriendStatus.ACCEPTED);
        long count = StreamSupport.stream(friendships.spliterator(), false).count();
        assertEquals(6, count);
    }

    @Test
    @Transactional
    void shouldInsertFriendship(){
        Iterable<Friendship> friendships = friendshipService.findAll();
        long initialCount = StreamSupport.stream(friendships.spliterator(), false).count();
        createFriendship();
        Iterable<Friendship> friendships2 = friendshipService.findAll();
        long finalCount = StreamSupport.stream(friendships2.spliterator(), false).count();
        assertEquals(initialCount + 1, finalCount);
    }

    @Test
    @Transactional
    void shouldUpdateFriendship(){
        FriendshipDTO friendshipDTO = new FriendshipDTO();
        friendshipDTO.setSender(4);
        friendshipDTO.setReceiver(6);
        Friendship friendship = this.friendshipService.create(friendshipDTO);
        assertEquals(friendship.getFriendState(), FriendStatus.PENDING);
        friendshipDTO.setFriendship_state(FriendStatus.ACCEPTED);
        Friendship updatedFriendship = this.friendshipService.update(friendship.getId(), friendshipDTO);
        assertEquals(updatedFriendship.getFriendState(), FriendStatus.ACCEPTED);
    }

    @Test
    @Transactional
    void shouldDeleteFriendship(){
        Friendship friendship = createFriendship();
        Iterable<Friendship> friendships = friendshipService.findAll();
        long initialCount = StreamSupport.stream(friendships.spliterator(), false).count();
        friendshipService.delete(friendship.getId());
        Iterable<Friendship> friendships2 = friendshipService.findAll();
        long finalCount = StreamSupport.stream(friendships2.spliterator(), false).count();
        assertEquals(initialCount - 1, finalCount);
    }
}
