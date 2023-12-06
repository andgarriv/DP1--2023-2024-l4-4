package us.l4_4.dp1.end_of_line.friendship;

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
import us.l4_4.dp1.end_of_line.enums.FriendStatus;

@RestController
@RequestMapping("/api/v1/friendships")
@Tag(name = "Friendship", description = "API for the management of Friendships")
public class FriendshipController {
    
    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship getFriendshipById(@PathVariable Integer id) {
        return friendshipService.findFriendshipById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Friendship> getAllFriendships() {
        return friendshipService.findAllFriendships();
    }

    @GetMapping("/friends/{id}/{friendState}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Friendship> getAllFriendshipsByPlayerId(@PathVariable @Valid Integer id, @PathVariable @Valid FriendStatus friendState) {
        return friendshipService.findAllFriendshipsByPlayerId(id, friendState);
    }
    
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship create(@RequestBody @Valid FriendshipDTO friendshipDTO) {
        return friendshipService.createFriendship(friendshipDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship update(@PathVariable Integer id, @RequestBody @Valid FriendshipDTO friendshipDTO) {
        return friendshipService.updateFriendship(id, friendshipDTO); 
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        friendshipService.deleteFriendship(id);
    }
}
