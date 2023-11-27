package us.l4_4.dp1.end_of_line.player;

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

	@Transactional(readOnly = true)
	public Iterable<Player> findAll() throws DataAccessException{
		return playerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Player findById(Integer id) throws DataAccessException{
		return this.playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
	}

	@Transactional(readOnly = true)
	public Player findByNickname(String nickname) throws DataAccessException{
		return playerRepository.findByNickname(nickname)
		.orElseThrow(() -> new ResourceNotFoundException("Player", "nickname", nickname));	
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

	@Transactional
	public Player savePlayer(Player player) throws DataAccessException {
		playerRepository.save(player);
		return player;
	}

	@Transactional
	public Player updatePlayer(Integer nickname) throws DataAccessException{
		Player playerToUpdate = findById(nickname);
		BeanUtils.copyProperties(nickname, playerToUpdate, "id");
		return playerRepository.save(playerToUpdate);
	}	

	@Transactional
	public Player updatePlayer(Integer id, Player player){
		Player playerToUpdate = findById(id);
		BeanUtils.copyProperties(player, playerToUpdate, "id");
		return playerRepository.save(playerToUpdate);
	} 

	@Transactional
	public void deletePlayer(Integer id) throws DataAccessException{
		playerRepository.deletePlayer(id);
	}

	@Transactional
	public Boolean existsByNickname(String nickname) {
		return playerRepository.existsByNickname(nickname);
	}

	@Transactional
	public Boolean existsByEmail(String email) {
		return playerRepository.existsByEmail(email);
	}
}