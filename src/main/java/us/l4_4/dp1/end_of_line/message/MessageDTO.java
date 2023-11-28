package us.l4_4.dp1.end_of_line.message;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;

@Getter
@Setter
public class MessageDTO {

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    private Integer senderId;

    private Integer receiverId;

    private Integer gameId;

    public MessageDTO(){}

    public MessageDTO(Message message){ 
        this.color = message.getColor();
        this.reaction = message.getReaction();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
        this.gameId = message.getGameId();
    }
}
