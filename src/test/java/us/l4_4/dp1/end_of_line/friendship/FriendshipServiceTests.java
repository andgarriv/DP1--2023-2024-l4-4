package us.l4_4.dp1.end_of_line.friendship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.player.Friendship;
import us.l4_4.dp1.end_of_line.player.FriendshipService;

@SpringBootTest
@AutoConfigureTestDatabase
class FriendshipServiceTests {

    @Autowired
    private FriendshipService friendshipService;

    @Test
    void shouldFindFriendshipById() {
        Friendship friendship = friendshipService.findFriendshipById(1);
        assert(friendship.getSender().getId() == 3);
        assert(friendship.getReceiver().getId() == 4);
        assert(friendship.getFriendState().equals(FriendStatus.ACCEPTED));
    }

}
