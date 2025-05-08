package cl.ntt.userapi.user_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.ntt.userapi.user_api.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthenticationFilter jwtAuthenticationFilter;

	private static final String[] PATH_WHITELIST = {
			"/v1/user/create", "/h2-console/**", "/v1/auth/login/**"
	};

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("Configuring web security customizer");
		return web -> web.ignoring().requestMatchers(PATH_WHITELIST);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Configuring security filter chain");
		http
				.anonymous(AnonymousConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(PATH_WHITELIST).permitAll()
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/v3/api-docs/**").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> {
							response.setContentType("application/json");
							response.setCharacterEncoding("UTF-8");
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							String jsonResponse = """
									    {
									        "mensaje": "Acceso denegado"
									    }
									""";
							response.getWriter().write(jsonResponse);
						}))
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
