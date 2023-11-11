package org.springframework.samples.petclinic.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.auth.payload.request.SignupRequest;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final PasswordEncoder encoder;
	private final PlayerService playerService;

	@Autowired
	public AuthService(PasswordEncoder encoder, PlayerService playerService) {
		this.encoder = encoder;
		this.playerService = playerService;
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
		playerService.createPlayer(player);
		}
	}
