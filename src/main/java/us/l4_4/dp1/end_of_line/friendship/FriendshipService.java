package us.l4_4.dp1.end_of_line.friendship;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.FriendStatus;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.player.PlayerRepository;

@Service
public class FriendshipService {

    private FriendshipRepository friendshipRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Transactional(readOnly = true)
    public Friendship findFriendshipById(Integer id) throws DataAccessException{
        return friendshipRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Friendship", "id", id));
    }
    /*
    @Transactional(readOnly = true)
    public List<Friendship> findAllFriendshipByPlayerId(Integer id) throws DataAccessException{
        return friendshipRepository.findAllFriendshipsByPlayerId(id);
    }

    @Transactional(readOnly = true)
    public Friendship findFriendshipBySenderAndReceiver(Integer sender_id, Integer receiver_id) throws DataAccessException{
        return friendshipRepository.findFriendshipBySenderAndReceiver(sender_id, receiver_id).orElseThrow(() -> new ResourceNotFoundException("Friendship", "sender_id and receiver_id", sender_id + " and " + receiver_id));
    }*/

    @Transactional
    public Friendship createFriendship(FriendshipDTO friendshipDTO) throws DataAccessException{
        Friendship friendship = new Friendship();
        friendship.setSender(playerRepository.findById(friendshipDTO.getSender_id()).get());
        friendship.setReceiver(playerRepository.findById(friendshipDTO.getReceiver_id()).get());
        friendship.setFriendState(FriendStatus.PENDING);
        friendshipRepository.save(friendship);
        return friendship;
    }

    @Transactional
    public Friendship saveFriendship(Friendship friendship) throws DataAccessException{
        friendshipRepository.save(friendship);
        return friendship;
    }

    @Transactional
    public Friendship updateFriendship(Integer id, FriendshipDTO friendshipDTO) throws DataAccessException{
        Friendship friendshipToUpdate = findFriendshipById(id);
        BeanUtils.copyProperties(friendshipDTO, friendshipToUpdate, "id");
        return friendshipRepository.save(friendshipToUpdate);
    }

    @Transactional
    public void deleteFriendship(Integer id) throws DataAccessException{
        friendshipRepository.deleteById(id);
    }
}