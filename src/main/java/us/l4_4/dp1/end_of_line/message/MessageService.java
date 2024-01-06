package us.l4_4.dp1.end_of_line.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.game.GameService;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private GameService gameService;

    @Autowired
    public MessageService(MessageRepository messageRepository, GameService gameService) {
        this.messageRepository = messageRepository;
        this.gameService = gameService;
    }

    @Transactional(readOnly = true)
    public List<Message> findAllMessagesByGameId(Integer id) {
        return messageRepository.findAllMessagesByGameId(id);
    }

    @Transactional
    public Message save(MessageDTO messageDTO) throws DataAccessException{
        Message newMessage = new Message();
        newMessage.setColor(messageDTO.getColor());
        newMessage.setReaction(messageDTO.getReaction());
        messageRepository.save(newMessage);

        Game gameToUpdate = gameService.findById(messageDTO.getGameId());
        List<Message> ls = gameToUpdate.getMessage();
        ls.add(newMessage);
        gameToUpdate.setMessage(ls);
        gameService.save(gameToUpdate);

        return newMessage;
    }

    @Transactional
    public void delete(Integer id) {
        messageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Message findById(Integer id) throws DataAccessException{
        return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
    }

    
}
