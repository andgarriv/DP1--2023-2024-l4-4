package us.l4_4.dp1.end_of_line.player;

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
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.game.GameRepository;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayer;
import us.l4_4.dp1.end_of_line.gameplayer.GamePlayerRepository;

@Service
public class PlayerService {

	private PlayerRepository playerRepository;
	private FriendshipRepository friendshipRepository;
	private GameRepository gameRepository;
	private GamePlayerRepository gamePlayerRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository, FriendshipRepository friendshipRepository, 
			GameRepository gameRepository, GamePlayerRepository gamePlayerRepository) {
		this.playerRepository = playerRepository;
		this.friendshipRepository = friendshipRepository;
		this.gameRepository = gameRepository;	
		this.gamePlayerRepository = gamePlayerRepository;
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
		return playerRepository.save(player);
	}

	@Transactional
	public Player update(Integer id, Player player) {
		Player playerToUpdate = findById(id);
		BeanUtils.copyProperties(player, playerToUpdate, "id");
		return playerRepository.save(playerToUpdate);
	}

	@Transactional
	public void delete(Integer id) throws DataAccessException {
		List<Game> games = gameRepository.findGamesByPlayerId(id);
		Player deletePlayer = findById(15);
		for (int i = 0; i < games.size(); i++) {
			if (games.get(i).getGamePlayers().stream().map(gp -> gp.getPlayer()).collect(Collectors.toList()).contains(deletePlayer)) {
				gameRepository.delete(games.get(i));
			} else {
				GamePlayer gp = gamePlayerRepository.findGamePlayerByGameAndPlayer(id, games.get(i).getId());
				gp.setPlayer(deletePlayer);
				gamePlayerRepository.save(gp);
				Game g = games.get(i);
				g.setWinner(deletePlayer);
				gameRepository.save(g);
			}
		}
		playerRepository.save(deletePlayer); 
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