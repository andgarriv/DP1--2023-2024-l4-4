package us.l4_4.dp1.end_of_line.player;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.friendship.FriendshipRepository;

@Service
public class PlayerService {

	private PlayerRepository playerRepository;
	private FriendshipRepository friendshipRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository, FriendshipRepository friendshipRepository) {
		this.playerRepository = playerRepository;
		this.friendshipRepository = friendshipRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Player> findAll() throws DataAccessException {
		return playerRepository.findAllPlayers();
	}

	@Transactional(readOnly = true)
	public Player findById(Integer id) throws DataAccessException {
		return this.playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
	}

	@Transactional(readOnly = true)
	public Player findByNickname(String nickname) throws DataAccessException {
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
	public Player save(Player player) throws DataAccessException {
		LocalDate now = LocalDate.now();
		LocalDate playerDate = player.getBirthDate();
		LocalDate minDate = now.minusYears(7);
		if (minDate.isBefore(playerDate))
			throw new IllegalArgumentException("You must be at least 7 years old to register");
		playerRepository.save(player);
		return player;
	}

	@Transactional
	public Player update(Integer id, Player player) {
		Player playerToUpdate = findById(id);
		BeanUtils.copyProperties(player, playerToUpdate, "id");
		return playerRepository.save(playerToUpdate);
	}

	@Transactional
	public void delete(Integer id) throws DataAccessException {
		playerRepository.deleteById(id);
	}

	@Transactional
	public Boolean existsByNickname(String nickname) {
		return playerRepository.existsByNickname(nickname);
	}

	@Transactional
	public Boolean existsByEmail(String email) {
		return playerRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public List<Player> findAllFriendsByPlayerId(Integer playerId) throws DataAccessException {
		return StreamSupport.stream(friendshipRepository.findAllFriendshipsByPlayerId(playerId).spliterator(), false)
				.filter(friendship -> friendship.getFriendState().equals(FriendStatus.ACCEPTED))
				.map(friendship -> friendship.getSender().getId().equals(playerId) ? friendship.getReceiver()
						: friendship.getSender())
				.collect(Collectors.toList());
	}

}