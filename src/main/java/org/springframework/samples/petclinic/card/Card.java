package org.springframework.samples.petclinic.card;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.game.Color;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


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
    @Range(min = 0, max = 6)
    @Column(name = "card_row")
    Integer row;
    
    @NotNull
    @Range(min = 0, max = 6)
    @Column(name = "card_column")
    Integer column;
}
