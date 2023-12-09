package us.l4_4.dp1.end_of_line.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Message> findAll() throws DataAccessException{
        return messageRepository.findAll();
    }

    @Transactional
    public Message save(Message message) throws DataAccessException{
        return messageRepository.save(message);
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
