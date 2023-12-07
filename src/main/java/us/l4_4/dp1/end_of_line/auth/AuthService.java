package us.l4_4.dp1.end_of_line.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.auth.payload.request.SignupRequest;
import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerService;

@Service
public class AuthService {

	public final PasswordEncoder encoder;
	public final PlayerService playerService;

	@Autowired
	public AuthService(PasswordEncoder encoder, PlayerService playerService) {
		this.encoder = encoder;
		this.playerService = playerService;
	}

	public String encodePassword(String password) {
		return encoder.encode(password);
	}

	@Transactional
	public void createUser(@Valid SignupRequest request) {
		Player player = new Player();
		player.setName(request.getName());
		player.setSurname(request.getSurname());
		player.setPassword(encoder.encode(request.getPassword()));
		player.setEmail(request.getEmail());
		player.setBirthDate(request.getBirthdate());
		player.setNickname(request.getNickname());
		player.setAvatar(request.getAvatar());
		playerService.save(player);
		}
	}
