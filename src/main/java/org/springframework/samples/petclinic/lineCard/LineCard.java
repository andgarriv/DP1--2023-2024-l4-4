package org.springframework.samples.petclinic.lineCard;

import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class LineCard extends BaseEntity{
    
    Integer initiative;
    Integer entry;
    List<Integer> exit;
    
    
}