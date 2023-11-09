package org.springframework.samples.petclinic.card;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.game.Color;
import org.springframework.samples.petclinic.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Card extends BaseEntity{

    @NotNull
    Color color;

    @NotNull
    @Range(min = 0, max = 6)
    Integer row;
    
    @NotNull
    @Range(min = 0, max = 6)
    Integer column;
    
}
