package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
	
	private PlayerRepository playerRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Transactional
	public Boolean existsPlayerByNickname(String nickname) {
		return playerRepository.existsByNickname(nickname);
	}

	@Transactional
	public Boolean existsPlayerByEmail(String email) {
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
	public Player findPlayerByNickname(String nickname){
		if(nickname == null){
			throw new ResourceNotFoundException("nickname");
		}
		return playerRepository.findPlayerByName(nickname);	
	}

	@Transactional
	public void deletePlayer(String nickname){
		playerRepository.deletePlayer(nickname);
	}

	@Transactional
	public Player updatePlayer(String nickname){
		Player oldPlayer = findPlayerByNickname(nickname);
		BeanUtils.copyProperties(nickname, oldPlayer, "id");
		return playerRepository.save(oldPlayer);
		}
	}



