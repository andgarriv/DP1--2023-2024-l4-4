package us.l4_4.dp1.end_of_line.card;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
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

    
    @Range(min = 0, max = 6)
    @Column(name = "card_row")
    Integer row;
    
    @Range(min = 0, max = 6)
    @Column(name = "card_column")
    Integer column;
}
