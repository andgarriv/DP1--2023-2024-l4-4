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
import us.l4_4.dp1.end_of_line.message.Message;
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
    @Transactional
    void shouldCreateMessage() {
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
    void shouldEditMessage() {
        // Asegurarse de que el mensaje exista
        Message existingMessage = new Message();
        existingMessage.setColor(Color.RED);
        existingMessage.setReaction(Reaction.GG);
        Message savedMessage = messageRepository.save(existingMessage);

        // Modificar el mensaje
        savedMessage.setColor(Color.BLUE); // Cambiar a un nuevo color
        savedMessage.setReaction(Reaction.NICE); // Cambiar a una nueva reacción

        // Llamada al método que se está probando
        Message updatedMessage = messageService.save(savedMessage);

        // Verificación
        assertEquals(savedMessage.getId(), updatedMessage.getId());
        assertEquals(Color.BLUE, updatedMessage.getColor());
        assertEquals(Reaction.NICE, updatedMessage.getReaction());
    }

    @Test
    @Transactional
    void shouldDeleteMessage() {
        Integer firstCount = ((Collection<Message>) messageRepository.findAll()).size();
        // Crear un mensaje de prueba
        Message existingMessage = new Message();
        existingMessage.setId(10); 
        existingMessage.setColor(Color.RED);
        existingMessage.setReaction(Reaction.GG);
        messageRepository.save(existingMessage);

        Integer secondCount = ((Collection<Message>) messageRepository.findAll()).size();
        assertEquals(firstCount + 1, secondCount);
        // Llamada al método que se está probando
        messageService.delete(1);
        Integer lastCount = ((Collection<Message>) messageRepository.findAll()).size();
        assertEquals(firstCount, lastCount);
        // Verificación
        
    }
    
}
