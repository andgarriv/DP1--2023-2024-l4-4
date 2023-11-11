package org.springframework.samples.petclinic.startCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.card.Card;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
public class StartCard extends Card {
    
    @NotNull
    @Value("2")
    Integer exit;
}
