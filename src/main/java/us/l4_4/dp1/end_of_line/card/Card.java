package us.l4_4.dp1.end_of_line.card;

import java.util.Date;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.CardStatus;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Orientation;
import us.l4_4.dp1.end_of_line.game.Game;
import us.l4_4.dp1.end_of_line.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

    @NotNull
    @Range(min = 0, max = 5)
    Integer iniciative;

    @NotNull
    @Enumerated(EnumType.STRING)
    Color color;

    @NotNull
    @Enumerated(EnumType.STRING)
    Exit exit;

    @NotNull
    @Enumerated(EnumType.STRING)
    Orientation orientation;

    @Enumerated(EnumType.STRING)
    CardStatus card_Status;

    @Range(min = 0, max = 6)
    @Column(name = "card_row")
    Integer row;
    
    @Range(min = 0, max = 6)
    @Column(name = "card_column")
    Integer column;

    @Column(name = "is_template")
    Boolean is_Template;

    @Transient
    Date timeStamp;

}
