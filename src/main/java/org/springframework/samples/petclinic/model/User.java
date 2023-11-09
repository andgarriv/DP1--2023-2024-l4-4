package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class User extends BaseEntity{
    
	@NotBlank
	protected String name;

    @NotBlank
    protected String surname;

    @NotBlank
    @Size(min=5, max = 40)
    protected String password;

    @Email
    @Column(unique = true)
    protected String email;

    @NotBlank
    protected LocalDate birthDate;
}
