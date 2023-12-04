package us.l4_4.dp1.end_of_line.friendship;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.exceptions.BadRequestException;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class FriendshipService {

    FriendshipRepository friendshipRepository;

    PlayerRepository playerRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, PlayerRepository playerRepository) {    
        this.friendshipRepository = friendshipRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional(readOnly = true)
    public Friendship findFriendshipById(Integer id) throws DataAccessException{
        return friendshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Friendship", "id", id));
    }

    @Transactional(readOnly = true)
    public Iterable<Friendship> findAllFriendships() throws DataAccessException{
        return friendshipRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Friendship> findAllFriendshipsByPlayerId(Integer id) throws DataAccessException{
        return friendshipRepository.findAllFriendshipsByPlayerId(id);
    }

    @Transactional(readOnly = true)
    public Friendship findFriendshipBySenderAndReceiver(Integer sender_id, Integer receiver_id) throws DataAccessException{
        return friendshipRepository.findFriendshipBySenderAndReceiver(sender_id, receiver_id).orElseThrow(() -> new ResourceNotFoundException("Friendship", "sender_id and receiver_id", sender_id + " and " + receiver_id));
    }

    private Boolean existsFriendship(Integer sender_id, Integer receiver_id) throws DataAccessException{
        return friendshipRepository.findFriendshipBySenderAndReceiver(sender_id, receiver_id).isPresent();
    }

    @Transactional
    public Friendship createFriendship(FriendshipDTO friendshipDTO) throws DataAccessException{
        if (existsFriendship(friendshipDTO.sender, friendshipDTO.receiver))
            throw new BadRequestException("No se puede crear una amistad entre dos jugadores que ya son amigos");
        if(!playerRepository.existsPlayerById(friendshipDTO.sender) || !playerRepository.existsPlayerById(friendshipDTO.receiver))
            throw new BadRequestException("No existe el jugador con id " + friendshipDTO.sender + " o " + friendshipDTO.receiver);
        Friendship friendship = new Friendship();
        friendship.setSender(playerRepository.findById(friendshipDTO.sender).get());
        friendship.setReceiver(playerRepository.findById(friendshipDTO.receiver).get());
        friendship.setFriendState(FriendStatus.PENDING);
        return friendshipRepository.save(friendship);
    }

    @Transactional
    public Friendship saveFriendship(Friendship friendship) throws DataAccessException{
        friendshipRepository.save(friendship);
        return friendship;
    }

    @Transactional
    public Friendship updateFriendship(Integer id, FriendshipDTO friendshipDTO) throws DataAccessException{
        Friendship friendshipToUpdate = findFriendshipById(id);
        friendshipToUpdate.setFriendState(friendshipDTO.getFriendship_state());
        return friendshipRepository.save(friendshipToUpdate);
    }

    @Transactional
    public void deleteFriendship(Integer id) throws DataAccessException{
        friendshipRepository.deleteById(id);
    }
}