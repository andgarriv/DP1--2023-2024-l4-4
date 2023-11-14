package us.l4_4.dp1.end_of_line.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import us.l4_4.dp1.end_of_line.authorities.Authorities;
import us.l4_4.dp1.end_of_line.player.Player;


/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

  @Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Player player = new Player();
		Authorities authorities = new Authorities();
		LocalDate birthdate = LocalDate.of(1999, 12, 12);
		authorities.setAuthority("PLAYER");
		player.setName("");
		player.setSurname("Garcia Escudero");
		player.setPassword("Adm1n*");
		player.setEmail("angelgares@gmail.com");
		player.setBirthDate(birthdate);
		player.setNickname("Angelgares122003");
		player.setAvatar("https://i.imgur.com/1.jpg");
		player.setAuthority(authorities);

		Validator validator = createValidator();
		Set<ConstraintViolation<Player>> constraintViolations = validator.validate(player);

		assertThat(constraintViolations.size()).isEqualTo(2);
		ConstraintViolation<Player> violation2 = constraintViolations.iterator().next();
		assertThat(violation2.getPropertyPath().toString()).isEqualTo("nickname");
		assertThat(violation2.getMessage()).isEqualTo("Nickname must be between 5 and 15 characters");
	}

}
