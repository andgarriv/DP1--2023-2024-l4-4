package us.l4_4.dp1.end_of_line.configuration;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import us.l4_4.dp1.end_of_line.configuration.services.PlayerDetailsImpl;

@TestConfiguration
public class SpringSecurityWebAuxTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        PlayerDetailsImpl playerActiveUser = new PlayerDetailsImpl(1, "player", "password",
        		Arrays.asList(
                        new SimpleGrantedAuthority("PLAYER"))
        );

        PlayerDetailsImpl adminActiveUser = new PlayerDetailsImpl(1, "admin", "password",
        		Arrays.asList(
                        new SimpleGrantedAuthority("ADMIN"))
        );

        return new InMemoryUserDetailsManager(Arrays.asList(
        		playerActiveUser, adminActiveUser
        ));
    }
}
