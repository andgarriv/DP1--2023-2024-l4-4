package us.l4_4.dp1.end_of_line.player;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.auth.AuthService;
import us.l4_4.dp1.end_of_line.authorities.AuthoritiesService;
import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.friendship.Friendship;
import us.l4_4.dp1.end_of_line.friendship.FriendshipService;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.game.GameService;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerService;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Player", description = "API for the management of Player")
@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private PlayerService playerService;
    private AuthService authService;
    public final PasswordEncoder encoder;
    private AuthoritiesService authoritiesService;
    private GameService gameService;
    private FriendshipService friendshipService;
    private GamePlayerService gamePlayerService;

    @Autowired
    public PlayerController(PlayerService playerService, AuthService authService, PasswordEncoder encoder,
            AuthoritiesService authoritiesService, GameService gameService, FriendshipService friendshipService,
            GamePlayerService gamePlayerService) {
        this.playerService = playerService;
        this.authService = authService;
        this.encoder = encoder;
        this.authoritiesService = authoritiesService;
        this.gameService = gameService;
        this.friendshipService = friendshipService;
        this.gamePlayerService = gamePlayerService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Player> findAll() {
        return playerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player findById(@PathVariable Integer id) {
            return playerService.findById(id);
    }

    @GetMapping("/nickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public Player findByNickname(@PathVariable String nickname) {
            return playerService.findByNickname(nickname);
    }

    @GetMapping("/{id}/games/{gameId}/gameplayer")
    @ResponseStatus(HttpStatus.OK)
    public GamePlayer findGamePlayerByGameAndPlayer(@PathVariable Integer id, @PathVariable Integer gameId){
        return gamePlayerService.findGamePlayerByGameAndPlayer(id, gameId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> findAllFriendsByPlayerId(@PathVariable Integer id) {
        return playerService.findAllFriendsByPlayerId(id);
    }

    @GetMapping("/{id}/friendships/{friendState}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Friendship> findAllFriendshipsByPlayerId(@PathVariable @Valid Integer id, @PathVariable @Valid FriendStatus friendState) {
        return friendshipService.findAllFriendshipsByPlayerId(id, friendState);
    }

    @GetMapping("/{id}/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findAllGamesByPlayerId(@PathVariable Integer id) {
        return gameService.findAllGamesByPlayerId(id);
    }

    @GetMapping("/{id}/games/notended")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> findNotEndedGamesByPlayerId(@PathVariable Integer id) {
        return gameService.findNotEndedGamesByPlayerId(id);
    }

    @GetMapping("/{id}/statistics")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getGameStatisticsByPlayerId(@PathVariable Integer id) {
        return gameService.calculateStatisticsByPlayerId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody @Valid Player player) {
        String password2 = authService.encodePassword(player.getPassword());
        player.setPassword(password2);
        player.setAuthority(authoritiesService.findByAuthority("PLAYER"));
        playerService.save(player);
        return player;
    }

    @PostMapping("/{id}/playerachievements")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayerAchievement(@PathVariable Integer id) {
        gameService.createPlayerAchievement(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player update(@PathVariable Integer id, @RequestBody @Valid Player player) {
        return playerService.update(id, player);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        playerService.delete(id);
    }
}
