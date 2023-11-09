package org.springframework.samples.petclinic.lineCard;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.card.Card;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LineCard extends Card{
    
    @NotNull
    @Range(min = 0, max = 5)
    Integer initiative;

    @NotNull
    Integer entry;

    @NotEmpty
    List<Integer> exit;
    
    
}