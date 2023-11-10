package org.springframework.samples.petclinic.admin;



import org.springframework.samples.petclinic.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin extends User{
    
}
