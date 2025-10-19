package com.lmh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lmh.service.IUsuarioService;
import com.lmh.service.impl.UserDetailsServiceImpl;
import com.lmh.view.LoginView;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;

@EnableWebSecurity
@Configuration
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public class SecurityConfig {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_SUCCESS_URL = "/salas";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";

	private IUsuarioService usuarioService;

	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

	@Bean
	@Order(1)
	public SecurityFilterChain vaadinSecurity(HttpSecurity http) throws Exception {
		http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
			configurer.loginView(LoginView.class);
		});

        http.formLogin(login -> login.defaultSuccessUrl("/salas", true));
		return http.build();
	}

	@Bean
	@Order(0)
	public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**", "/ws-bingo/**") // solo endpoints REST + WebSocket
                .csrf(csrf -> csrf.disable()).headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll().requestMatchers("/ws-bingo/**").permitAll().requestMatchers("/api/**").permitAll() // WebSocket abierto
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                .anyRequest().authenticated() // resto de API requiere login
                );
		return http.build();
	}
	
	/*@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/	
}
