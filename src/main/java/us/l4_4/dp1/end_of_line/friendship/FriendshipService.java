package us.l4_4.dp1.end_of_line.friendship;

import java.util.Optional;

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
    public Iterable<Friendship> findAllFriendshipsByPlayerId(Integer id) throws DataAccessException{
        return friendshipRepository.findAllFriendshipsByPlayerId(id);
    }

    private Boolean checkFriendship(Integer sender_id, Integer receiver_id) throws DataAccessException {
        if (sender_id.equals(receiver_id)) 
            throw new BadRequestException("You cannot create a friendship with yourself.");
        
        if (!playerRepository.existsPlayerById(sender_id) || !playerRepository.existsPlayerById(receiver_id)) 
            throw new BadRequestException("Player with id " + sender_id + " or " + receiver_id + " does not exist.");
        
        Optional<Friendship> optionalFriendship = friendshipRepository.findFriendshipBySenderAndReceiver(sender_id, receiver_id);
        if (optionalFriendship.isPresent()) {
            FriendStatus friendStatus = optionalFriendship.get().getFriendState();
            switch (friendStatus) {
                case PENDING:
                    throw new BadRequestException("There is already a pending friendship request with this player.");
                case REJECTED:
                    throw new BadRequestException("You cannot create a friendship with a player who has rejected you.");
                default:
                    throw new BadRequestException("You are already friends with this player.");
            }
        }
        return true;
    }
    

    @Transactional
    public Friendship createFriendship(FriendshipDTO friendshipDTO) throws DataAccessException{
        checkFriendship(friendshipDTO.sender, friendshipDTO.receiver);
        Friendship friendship = new Friendship();
        friendship.setSender(playerRepository.findById(friendshipDTO.sender).orElseThrow(() -> new BadRequestException("Player with id " + friendshipDTO.sender + " does not exist.")));
        friendship.setReceiver(playerRepository.findById(friendshipDTO.receiver).orElseThrow(() -> new BadRequestException("Player with id " + friendshipDTO.receiver + " does not exist.")));
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