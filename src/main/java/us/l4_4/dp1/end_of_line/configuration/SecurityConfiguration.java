package us.l4_4.dp1.end_of_line.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import us.l4_4.dp1.end_of_line.configuration.jwt.AuthEntryPointJwt;
import us.l4_4.dp1.end_of_line.configuration.jwt.AuthTokenFilter;
import us.l4_4.dp1.end_of_line.configuration.services.PlayerDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	PlayerDetailsServiceImpl playerDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	DataSource dataSource;

	private static final String ADMIN = "ADMIN";

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.csrf(AbstractHttpConfigurer::disable)		
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))			
			.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
			.exceptionHandling((exepciontHandling) -> exepciontHandling.authenticationEntryPoint(unauthorizedHandler))			
			
			.authorizeHttpRequests(authorizeRequests ->	authorizeRequests
			.requestMatchers("/resources/**", "/webjars/**", "/static/**", "/swagger-resources/**").permitAll()			
			.requestMatchers("/", "/oups","/api/v1/auth/**","/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll()												
			.requestMatchers("/api/v1/developers").hasAuthority(ADMIN)	

			.requestMatchers(HttpMethod.GET, "api/v1/players/all").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.DELETE, "api/v1/players/**").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.POST, "api/v1/players").permitAll()	
			.requestMatchers("/api/v1/players/**").authenticated()

			.requestMatchers(HttpMethod.GET,"/api/v1/friendships/all").hasAuthority(ADMIN)
			.requestMatchers("/api/v1/friendships/**").authenticated()
			
			.requestMatchers(HttpMethod.GET,"/api/v1/achievements/**").authenticated()
			.requestMatchers("/api/v1/achievements/**").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.POST,"/api/v1/achievements").hasAuthority(ADMIN)
			.requestMatchers(HttpMethod.GET,"/api/v1/achievements").authenticated()
			
			.requestMatchers(HttpMethod.GET,"/api/v1/games/all").authenticated()
			.requestMatchers("/api/v1/games/**").authenticated()

			.requestMatchers("/api/v1/cards").authenticated()
			.requestMatchers("/api/v1/cards/**").authenticated()

			.requestMatchers(HttpMethod.POST,"/api/v1/messages").authenticated()
			.requestMatchers("/api/v1/messages").authenticated()
			.requestMatchers("/api/v1/messages/**").authenticated()

			
			.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
			.anyRequest().authenticated())					
			
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);		
		return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}