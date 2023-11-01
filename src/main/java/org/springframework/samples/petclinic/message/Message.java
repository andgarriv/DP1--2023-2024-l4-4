package org.springframework.samples.petclinic.message;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Message {

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

}
