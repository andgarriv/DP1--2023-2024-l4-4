package us.l4_4.dp1.end_of_line.messages;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;
import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageDTO;
import us.l4_4.dp1.end_of_line.message.MessageRepository;
import us.l4_4.dp1.end_of_line.message.MessageService;

@SpringBootTest
@AutoConfigureTestDatabase
class MessageServiceTest {

    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceTest(MessageService messageService){
        this.messageService=messageService;
    }

    @Test
    void shouldFindMessageById(){
        Message message = this.messageService.findById(1);
        assertEquals(1, message.getId());
    }
    @Test 
    void shouldFindAllMessagesByGameId(){
        MessageDTO dto = new MessageDTO();
        dto.setGameId(17);
        dto.setColor(Color.RED);
        dto.setReaction(Reaction.GG);
        messageService.save(dto);
        Collection<Message> messages = this.messageService.findAllMessagesByGameId(17);
        assertEquals(6, messages.size());
    }
    
    @Test
    @Transactional
    void shouldCreateMessage() {
        MessageDTO dto = new MessageDTO();
        dto.setGameId(17);
        dto.setColor(Color.RED);
        dto.setReaction(Reaction.GG);
  
        Message savedMessage = messageService.save(dto);
        Message messageCreado = messageService.findById(savedMessage.getId());
        assertEquals(Color.RED,messageCreado.getColor());   
        assertEquals(Reaction.GG,messageCreado.getReaction());
    }



    @Test
    @Transactional
    void shouldDeleteMessage() {
        Message existingMessage = new Message();
        existingMessage.setColor(Color.RED);
        existingMessage.setReaction(Reaction.GG);
        Message creado = messageRepository.save(existingMessage);
        messageService.delete(creado.getId());
        assertThrows(ResourceNotFoundException.class, () -> messageService.findById(creado.getId()));
        
    }
    
}
