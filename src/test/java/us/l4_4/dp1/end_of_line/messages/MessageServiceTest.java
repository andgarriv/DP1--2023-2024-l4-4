package us.l4_4.dp1.end_of_line.messages;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;
import us.l4_4.dp1.end_of_line.message.Message;
import us.l4_4.dp1.end_of_line.message.MessageRepository;
import us.l4_4.dp1.end_of_line.message.MessageService;

public class MessageServiceTest {

    
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceTest(MessageService messageService){
        this.messageService=messageService;
    }
    

    @Test
    public void shouldFindMessageById(){
        Message message = this.messageService.findMessageById(1);
        assertEquals(1, message.getId());
    }
    
    @Test
    @Transactional
    public void shouldCreateMessage() {
        Color color = Color.RED;
        Reaction reaction = Reaction.GG;
        // Crear un mensaje de prueba con propiedades válidas
        Message newMessage = new Message();
        newMessage.setColor(color);
        newMessage.setReaction(reaction);
        // Llamada al método que se está probando
        Message savedMessage = messageService.save(newMessage);
        // Verificación
        assertEquals(newMessage, savedMessage);
    }

    @Test  
    @Transactional
    public void shouldEditMessage() {
        // Crear un mensaje de prueba
        Message existingMessage = new Message();
        existingMessage.setId(1); 
        existingMessage.setColor(Color.RED);
        existingMessage.setReaction(Reaction.GG);
        // Llamada al método que se está probando
        Message savedMessage = messageService.save(existingMessage);
        // Verificación
        assertEquals(existingMessage, savedMessage);
    }

    @Test
    @Transactional
    public void shouldDeleteMessage() {
        // Crear un mensaje de prueba
        Message existingMessage = new Message();
        existingMessage.setId(1); 
        existingMessage.setColor(Color.RED);
        existingMessage.setReaction(Reaction.GG);
        // Llamada al método que se está probando
        messageService.deleteById(existingMessage.getId());
        // Verificación
        assertNull(messageRepository.findMessageById(existingMessage.getId()));
    }
    
}
