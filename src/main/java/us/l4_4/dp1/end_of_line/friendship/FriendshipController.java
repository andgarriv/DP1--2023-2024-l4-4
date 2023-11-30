package us.l4_4.dp1.end_of_line.friendship;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/player/friendship")
@Tag(name = "Friendship", description = "API for the management of Friendships")
public class FriendshipController {
    
    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship getFriendshipById(@PathVariable Integer id) {
        return friendshipService.findFriendshipById(id);
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Friendship> getAllFriendshipsByPlayerId(@PathVariable @Valid Integer id) {
        return friendshipService.findAllFriendshipsByPlayerId(id);
    }

    @GetMapping("/find/{sender_id}/{receiver_id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship getFriendshipBySenderAndReceiver(@PathVariable Integer sender_id, @PathVariable Integer receiver_id) {
        return friendshipService.findFriendshipBySenderAndReceiver(sender_id, receiver_id);
    }
    
    @PostMapping("/{sender_id}/{receiver_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship createFriendship(@PathVariable Integer sender_id, @PathVariable Integer receiver_id) {
        return friendshipService.createFriendship(sender_id, receiver_id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship updateFriendship(@PathVariable Integer id, @RequestBody @Valid FriendshipDTO friendshipDTO) {
        return friendshipService.updateFriendship(id, friendshipDTO); 
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriendship(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
    }
}
