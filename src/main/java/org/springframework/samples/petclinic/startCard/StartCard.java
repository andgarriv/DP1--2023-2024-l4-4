package org.springframework.samples.petclinic.startCard;
import org.springframework.samples.petclinic.card.Card;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class StartCard extends Card {
    
    

    @NotNull
    Integer exit;


}
