package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import org.springframework.samples.petclinic.authorities.Authorities;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    //@Size(min=5, max = 40)
    protected String password;

    @Email
    @Column(unique = true)
    protected String email;

    @NotBlank
    protected LocalDate birthDate;

    @NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "authority")
	Authorities authority;

	public Boolean hasAuthority(String auth) {
		return authority.getAuthority().equals(auth);
	}

	public Boolean hasAnyAuthority(String... authorities) {
		Boolean cond = false;
		for (String auth : authorities) {
			if (auth.equals(authority.getAuthority()))
				cond = true;
		}
		return cond;
    }
}
