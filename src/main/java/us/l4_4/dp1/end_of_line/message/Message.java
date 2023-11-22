package us.l4_4.dp1.end_of_line.message;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Reaction;
import us.l4_4.dp1.end_of_line.model.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;
}