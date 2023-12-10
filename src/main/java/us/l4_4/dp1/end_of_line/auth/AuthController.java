package us.l4_4.dp1.end_of_line.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.auth.payload.request.LoginRequest;
import us.l4_4.dp1.end_of_line.auth.payload.request.SignupRequest;
import us.l4_4.dp1.end_of_line.auth.payload.response.JwtResponse;
import us.l4_4.dp1.end_of_line.auth.payload.response.MessageResponse;
import us.l4_4.dp1.end_of_line.configuration.jwt.JwtUtils;
import us.l4_4.dp1.end_of_line.configuration.services.PlayerDetailsImpl;
import us.l4_4.dp1.end_of_line.player.PlayerService;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "The Authentication API based on JWT")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final AuthService authService;
	private final PlayerService playerService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			AuthService authService, PlayerService playerService) {
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
		this.authService = authService;
		this.playerService = playerService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try{
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			PlayerDetailsImpl playerDetails = (PlayerDetailsImpl) authentication.getPrincipal();
			List<String> roles = playerDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

			return ResponseEntity.ok().body(new JwtResponse(jwt, playerDetails.getId(), playerDetails.getUsername(), roles));
		}catch(BadCredentialsException exception){
			return ResponseEntity.badRequest().body("Bad Credentials!");
		}
	}

	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
		Boolean isValid = jwtUtils.validateJwtToken(token);
		return ResponseEntity.ok(isValid);
	}

	
	@PostMapping("/signup")	
	public ResponseEntity<MessageResponse> registerPlayer(@Valid @RequestBody SignupRequest signUpRequest) {
		if(playerService.existsByNickname(signUpRequest.getNickname()).equals(true)){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Nickname is already taken!"));
		}
		if(playerService.existsByEmail(signUpRequest.getEmail()).equals(true)){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}
		authService.createUser(signUpRequest);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
