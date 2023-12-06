package us.l4_4.dp1.end_of_line.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Player", description = "API for the management of Player")
@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private PlayerService playerService;
    private AuthService authService;
    public final PasswordEncoder encoder;
    private AuthoritiesService authoritiesService;

    @Autowired
    public PlayerController(PlayerService playerService, AuthService authService, PasswordEncoder encoder,
            AuthoritiesService authoritiesService) {
        this.playerService = playerService;
        this.authService = authService;
        this.encoder = encoder;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Player> findAllPlayers() {
        return playerService.findAllPlayers();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody @Valid Player player) {
        String password2 = authService.encodePassword(player.getPassword());
        player.setPassword(password2);
        player.setAuthority(authoritiesService.findByAuthority("PLAYER"));
        playerService.savePlayer(player);
        return player;
    }

    @GetMapping("/nickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public Player findByNickname(@PathVariable String nickname) {
        if (playerService.findByNickname(nickname) != null)
            return playerService.findByNickname(nickname);
        else
            return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player findById(@PathVariable Integer id) {
        if (playerService.findById(id) != null)
            return playerService.findById(id);
        else
            return null;
    }

    @GetMapping("/allExcept/{id}")
    public ResponseEntity<List<Player>> getAllPlayersExceptWithId(@PathVariable Integer id) {
        List<Player> players = playerService.findAllPlayersExceptWithId(id);
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player update(@PathVariable Integer id, @RequestBody @Valid Player player) {
        if (playerService.findById(id) != null)
            return playerService.updatePlayer(id, player);
        else
            return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        playerService.deletePlayer(id);
    }
}
