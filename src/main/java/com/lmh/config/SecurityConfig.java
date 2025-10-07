package com.lmh.config;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.lmh.entity.QUsuario;
import com.lmh.entity.Usuario;
import com.lmh.service.IUsuarioService;
import com.lmh.utils.BeanFactory;
import com.lmh.view.LoginView;
import com.querydsl.core.types.Predicate;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;

import io.jsonwebtoken.io.IOException;
import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

	/*@Bean
    public SecurityFilterChain vaadinSecurity(HttpSecurity http) throws Exception {
        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });
        return http.build();
    }*/
    
    /*public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	/*http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });
    	
    	http
        .csrf(csrf -> csrf.disable()) // O configura CSRF para WebSocket si aplica
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws-bingo/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // <- SIEMPRE AL FINAL
         ).formLogin()
        .loginPage(("/login")).permitAll()
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/");
    	
    	
        return http.build();*/
    	
    	/*http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });*/

        // Configuración general
        /*http
            .csrf(csrf -> csrf.disable()) // WebSockets no usan CSRF tokens
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws-bingo/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // <- SIEMPRE AL FINAL
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
            );

        return http.build();
    }*/
	
	@Bean
    @Order(1)
    public SecurityFilterChain vaadinSecurity(HttpSecurity http) throws Exception {
        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });
        return http.build();
    }

    @Bean
    @Order(0)
    public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**", "/ws-bingo/**") // solo endpoints REST + WebSocket
                .csrf(csrf -> csrf.disable())
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/ws-bingo/**").permitAll()  // WebSocket abierto
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()                  // resto de API requiere login
                )
                .formLogin(login -> login
                        .loginPage(LOGIN_URL).permitAll()
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException, java.io.IOException {
                                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                                String username = userDetails.getUsername();

                                usuarioService = Option.of(usuarioService).getOrElse(BeanFactory.getBean(IUsuarioService.class));

                                Predicate p = QUsuario.usuario.nombreusuario.eq(username);

                                Usuario u = usuarioService.search(p).get(0);
                                u.setFechaultimoacceso(new Date());
                                u = usuarioService.save(u);

                                System.out.println("El usuario " + username + " ha iniciado sesión.");
                                response.sendRedirect(LOGIN_SUCCESS_URL);
                            }
                        })
                        .defaultSuccessUrl(LOGIN_SUCCESS_URL, true)
                        .failureUrl(LOGIN_FAILURE_URL)).logout(logout -> logout.logoutSuccessUrl(LOGOUT_SUCCESS_URL));
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        LoggerFactory.getLogger(SecurityConfig.class)
            .warn("NOT FOR PRODUCTION: Using in-memory user details manager!"); 
        var user = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();
        var admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();
       return new InMemoryUserDetailsManager(user, admin);
    }
    
    /*public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        
        String role = user.getUsername() == "admin" ? "ROLE_ADMIN" : "ROLE_USER";
        
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(),
            true, true, true, authorities);
    }*/
}
