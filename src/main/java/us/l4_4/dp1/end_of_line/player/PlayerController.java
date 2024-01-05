package us.l4_4.dp1.end_of_line.player;

import java.util.List;

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

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> findAllFriendsByPlayerId(@PathVariable Integer id) {
        return playerService.findAllFriendsByPlayerId(id);
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
