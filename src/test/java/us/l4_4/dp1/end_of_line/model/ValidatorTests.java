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
		player.setPassword("Adm1n!");
		player.setEmail("angelgares@gmail.com");
		player.setBirthDate(birthdate);
		player.setNickname("Angelgares");
		player.setAvatar("https://i.imgur.com/1.jpg");
		player.setAuthority(authorities);

		Validator validator = createValidator();
		Set<ConstraintViolation<Player>> constraintViolations = validator.validate(player);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Player> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}

}
