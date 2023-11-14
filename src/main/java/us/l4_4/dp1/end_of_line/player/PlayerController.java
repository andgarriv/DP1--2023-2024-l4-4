package us.l4_4.dp1.end_of_line.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
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


@RestController
@RequestMapping("/api/v1/player")
@Tag(name = "Player", description = "API for the management of Player")
@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private PlayerService playerService;
    private AuthService authService;
    public final PasswordEncoder encoder;

    @Autowired
    public PlayerController(PlayerService playerService, AuthService authService, PasswordEncoder encoder) {
        this.playerService = playerService;
        this.authService = authService;
        this.encoder = encoder;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody @Valid Player player){
        String password2 = authService.encodePassword(player.getPassword());
        player.setPassword(password2);
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setSurname(player.getSurname());
        newPlayer.setPassword(password2);
        newPlayer.setEmail(player.getEmail());
        newPlayer.setBirthDate(player.getBirthDate());
        newPlayer.setNickname(player.getNickname());
        newPlayer.setAvatar(player.getAvatar());
        // authService.createUser2(player);
        // return player;
        playerService.createPlayer(player);   
        return newPlayer;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player findById(@PathVariable Integer id){
        if (playerService.findPlayerById(id) != null) {
            return playerService.findPlayerById(id);
        }
        else{
            return null;
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player update(@PathVariable Integer id, @RequestBody @Valid Player player){
        if (playerService.findPlayerById(id) != null) {
            return playerService.updatePlayer(id, player);
        }
        else{
            return null;
        }
    }

    @GetMapping("/nickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public Player findByNickname(@PathVariable String nickname){
        if (playerService.findByNickname(nickname) != null) {
            return playerService.findByNickname(nickname);
        }
        else{
            return null;
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Player> findAll(){
        return playerService.findAllPlayers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
        playerService.deletePlayer(id);
    }
}
