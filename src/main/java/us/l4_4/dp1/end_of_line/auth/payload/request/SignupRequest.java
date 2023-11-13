package us.l4_4.dp1.end_of_line.auth.payload.request;

import java.util.Date;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	
	@NotBlank
	private String name;

	@NotBlank
	private String surname;
	
	@NotBlank
	@Size(min=5, max = 40)
	private String password;
	
	@Email
	private String email;
	
	@NotBlank
	private Date birthdate;

	@NotBlank
	@Size(min = 5, max = 15)
	private String nickname;

	@NotBlank
	@URL
	private String avatar;
}
