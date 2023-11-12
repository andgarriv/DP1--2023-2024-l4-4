package us.l4_4.dp1.end_of_line.configuration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.player.Player;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class PlayerDetailsServiceImpl implements UserDetailsService {
	@Autowired
	PlayerRepository playerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Player player = playerRepository.findByNickname(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return PlayerDetailsImpl.build(player);
	}

}
