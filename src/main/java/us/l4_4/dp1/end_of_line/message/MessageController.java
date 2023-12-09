package us.l4_4.dp1.end_of_line.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games/messages")
@Tag(name = "Message", description = "API for the management of Message")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody @Valid Message message) {
        return messageService.save(message);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Message> findAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Message findById(@PathVariable Integer id) {
        return messageService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Message update(@PathVariable Integer id,  @RequestBody Message message) {
        Message messageToUpdate = messageService.findById(id);
        messageToUpdate.setColor(message.getColor());
        messageToUpdate.setReaction(message.getReaction());
        messageService.save(messageToUpdate);
        return messageToUpdate;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        messageService.delete(id);
    }
}
