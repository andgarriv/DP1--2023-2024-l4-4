package us.l4_4.dp1.end_of_line.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

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
/*
    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Friendship> getAllFriendshipsByPlayerId(@PathVariable Integer id) {
        return friendshipService.findAllFriendshipByPlayerId(id);
    }

    @GetMapping("/find/{sender_id}/{receiver_id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship getFriendshipBySenderAndReceiver(@PathVariable Integer sender_id, @PathVariable Integer receiver_id) {
        return friendshipService.findFriendshipBySenderAndReceiver(sender_id, receiver_id);
    }*/
    /*
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship createFriendship(@RequestBody @Valid FriendshipDTO friendshipDTO) {
        return friendshipService.createFriendship(friendshipDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship updateFriendship(@PathVariable Integer id, @RequestBody @Valid FriendshipDTO friendshipDTO) {
        if(friendshipService.findFriendshipById(id) != null) 
            return friendshipService.updateFriendship(id, friendshipDTO);
        else
            return null; 
    }*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriendship(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
    }
}
