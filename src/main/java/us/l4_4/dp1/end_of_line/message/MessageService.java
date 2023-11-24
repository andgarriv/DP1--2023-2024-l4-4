package us.l4_4.dp1.end_of_line.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Transactional
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    public void deleteById(int id) {
        messageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Message findMessageById(int id) {
        return messageRepository.findMessageById(id);
    }
}
