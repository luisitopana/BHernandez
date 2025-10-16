package com.lmh.view;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lmh.entity.QUsuario;
import com.lmh.entity.Usuario;
import com.lmh.service.IUsuarioService;
import com.lmh.service.impl.SecurityService;
import com.lmh.utils.BeanFactory;
import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import io.vavr.control.Option;

@Route(value = "login") 
@PageTitle("Iniciar Sesión")
@AnonymousAllowed 
public class LoginView extends VerticalLayout{

	private LoginForm login = new LoginForm(); 

	private LoginI18n i18n = LoginI18n.createDefault();
	private LoginI18n.Form i18nForm = i18n.getForm();
	
	@Autowired
	private SecurityService securityService;
	
	private IUsuarioService usuarioService;
	
	public LoginView(@Autowired AuthenticationManager authenticationManager) {
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		i18nForm.setTitle("Inicio de Sesión");
		i18nForm.setUsername("Nombre de Usuario");
		i18nForm.setPassword("Contraseña");
		i18nForm.setSubmit("Iniciar Sesión");
		i18nForm.setForgotPassword("Olvidé Contraseña");
		i18n.setForm(i18nForm);
		
		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setTitle("Error");
		i18nErrorMessage.setMessage("El usuario o la contraseña no es correcto.");
		i18n.setErrorMessage(i18nErrorMessage);

		LoginForm loginForm = new LoginForm();
		loginForm.setI18n(i18n);
		loginForm.setAction("login");
		
		loginForm.addLoginListener(event -> {
            try {
                // Crear token de autenticación
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(event.getUsername(), event.getPassword());

                Authentication auth = authenticationManager.authenticate(token);

                // Guardar contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(auth);
                VaadinSession.getCurrent().setAttribute(Authentication.class, auth);
                
                usuarioService = Option.of(usuarioService).getOrElse(BeanFactory.getBean(IUsuarioService.class));

                Predicate p = QUsuario.usuario.nombreusuario.eq(event.getUsername());

                Usuario u = usuarioService.search(p).get(0);
                u.setFechaultimoacceso(new Date());
                u = usuarioService.save(u);

                System.out.println("El usuario " + event.getUsername() + " ha iniciado sesión.");
                // Redirigir a la vista principal de Vaadin
                getUI().ifPresent(ui -> ui.navigate("/salas"));

            } catch (Exception e) {
            	e.printStackTrace();
                loginForm.setError(true);
            }
        });
		
		add(new H1("Administración de BHernández"), loginForm);
	}
	
	private IUsuarioService getUsuarioService() {
		usuarioService = Option.of(usuarioService).getOrElse(BeanFactory.getBean(IUsuarioService.class));
		return usuarioService;
	}
}
