package us.l4_4.dp1.end_of_line.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.authorities.Authorities;

@Getter
@Setter
@MappedSuperclass
public class User extends BaseEntity{
    
	@NotBlank
	protected String name;

    @NotBlank
    protected String surname;

    @NotBlank
    protected String password;

    @Email
    @Column(unique = true)
    protected String email;

    @NotNull
    protected LocalDate birthDate;

	@NotBlank
    @Column(unique = true)
    @Size(min = 5, max = 15, message = "Nickname must be between 5 and 15 characters")
    protected String nickname;

    @NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "authority")
	protected Authorities authority;

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
