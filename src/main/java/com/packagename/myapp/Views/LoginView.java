package com.packagename.myapp.Views;

import com.packagename.myapp.MainLayout;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@PageTitle("AcademyTic")
public class LoginView extends LoginOverlay implements AfterNavigationObserver, BeforeEnterObserver {
    boolean isAuthenticated = false;
    public LoginView(){

        LoginI18n viewLogin = LoginI18n.createDefault();
        viewLogin.setHeader(new LoginI18n.Header());
        viewLogin.getHeader().setTitle("AcademyTic");
        viewLogin.getHeader().setDescription("Sistema de gestión académico y admistrativo");
        viewLogin.getForm().setUsername("Usuario");
        viewLogin.getForm().setTitle("Iniciar sesión");
        viewLogin.getForm().setPassword("Clave");
        viewLogin.getForm().setSubmit("Iniciar sesión");
        viewLogin.getForm().setForgotPassword("Olvidé mi clave");
        viewLogin.getErrorMessage().setTitle("Usuario/clave inválido");
        viewLogin.getErrorMessage().setMessage("Revise el usuario o clave e intente nuevamente");
        viewLogin.setAdditionalInformation("Derechos reservados Unidad Educativa Julia Navarrete Mendoza 2020");

        setForgotPasswordButtonVisible(false);
        setI18n(viewLogin);
        setAction("inicio");
        /*addLoginListener(e->{
            authenticate(e);
        });*/

    }
   /* private boolean authenticate(LoginEvent e) {

        if(LoginController.login(e.getUsername(),e.getPassword())) {
            return true;
        }else
            setError(true);
        setEnabled(true);
        return false;
    }*/

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGIN")!=null) {
            System.out.println("before true");
            beforeEnterEvent.forwardTo(MainLayout.class);
        } else {
            System.out.println("before false");
            setOpened(true);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        setError(afterNavigationEvent.getLocation().getQueryParameters().getParameters().containsKey(
                "error"));
    }


}
