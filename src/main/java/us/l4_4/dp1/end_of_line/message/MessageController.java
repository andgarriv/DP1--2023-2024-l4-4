package us.l4_4.dp1.end_of_line.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import us.l4_4.dp1.end_of_line.exceptions.BadRequestException;

@RestController
@RequestMapping("/api/v1/games/messages")
@Tag(name = "Message", description = "API for the management of Message")

public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@RequestBody @Valid Message message) {
        messageService.save(message);
        return message;
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable int id) {
        return messageService.findMessageById(id);
    }

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable int id, Message message) {
        Message messageToUpdate = messageService.findMessageById(id);
        if(messageToUpdate == null) {
            throw new BadRequestException("messageToUpdate is null");
        }
        messageToUpdate.setColor(message.getColor());
        messageToUpdate.setReaction(message.getReaction());
        messageService.save(messageToUpdate);
        return messageToUpdate;
    }



    
}
