package com.lmh.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login") 
@PageTitle("Iniciar Sesión")
@AnonymousAllowed 
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private LoginForm login = new LoginForm(); 

	private LoginI18n i18n = LoginI18n.createDefault();
	private LoginI18n.Form i18nForm = i18n.getForm();
	
	public LoginView(){
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
		
		add(new H1("Administración de BHernández"), loginForm);
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                 .getQueryParameters()
                 .getParameters()
                 .containsKey("error")) {
            login.setError(true); 
        }
    }
}
