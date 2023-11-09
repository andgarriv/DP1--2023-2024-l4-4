package org.springframework.samples.petclinic.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.clinic.Clinic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	
	// User
	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@NotBlank
	private String nickname;
	
	@NotBlank
	private String authority;

	@NotBlank
	private String password;
	
	@Email
	private String email;
	
	@NotBlank
	private String birthDate;

	//@URL
	private String avatar;


}
