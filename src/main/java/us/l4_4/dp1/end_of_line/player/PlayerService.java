package us.l4_4.dp1.end_of_line.player;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@Service
public class PlayerService {
	
	private PlayerRepository playerRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Transactional
	public Player savePlayer(Player player) throws DataAccessException {
		return playerRepository.save(player);
	}

	@Transactional
	public Boolean existsByNickname(String nickname) {
		return playerRepository.existsByNickname(nickname);
	}

	@Transactional
	public Boolean existsByEmail(String email) {
		return playerRepository.existsByEmail(email);
	}

	@Transactional
	public Player createPlayer(Player player){
		return playerRepository.save(player);
	}

	@Transactional(readOnly = true)
	public List<Player> findAllPlayers(){
		return playerRepository.findAllPlayers();
	}

	@Transactional(readOnly = true)
	public Player findByNickname(String nickname){
		return playerRepository.findByNickname(nickname)
		.orElseThrow(() -> new ResourceNotFoundException("Player", "nickname", nickname));	
	}

	@Transactional(readOnly = true)
	public Player findUserById(Integer id){
		if(id == null){
			throw new ResourceNotFoundException("id");
		}
		return playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
	}

	@Transactional
	public void deletePlayer(Integer id){
		playerRepository.deletePlayer(id);
	}

	@Transactional
	public Player updatePlayer(Integer nickname){
		Player oldPlayer = findUserById(null);
		BeanUtils.copyProperties(nickname, oldPlayer, "id");
		return playerRepository.save(oldPlayer);
		}

	@Transactional
	public Player updatePlayer(Integer id, Player player){
		Player oldPlayer = findUserById(id);
		BeanUtils.copyProperties(player, oldPlayer, "id");
		return playerRepository.save(oldPlayer);
		}

	@Transactional(readOnly = true)
	public Player findCurrentPlayer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new ResourceNotFoundException("Nobody authenticated!");
		else
			return playerRepository.findByNickname(auth.getName())
					.orElseThrow(() -> new ResourceNotFoundException("User", "Username", auth.getName()));
	
	}
}
