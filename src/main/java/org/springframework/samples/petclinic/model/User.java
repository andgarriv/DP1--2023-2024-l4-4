package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@MappedSuperclass
public class User extends BaseEntity{
    
	@NotEmpty
	protected String name;

    @NotEmpty
    protected String surname;

    @NotEmpty
    protected String password;

    @NotEmpty
    @Column(unique = true)
    protected String email;

    @NotEmpty
    protected LocalDate birthDate;

    protected Boolean isAdmin;
}
