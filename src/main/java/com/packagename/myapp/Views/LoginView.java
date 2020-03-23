package com.packagename.myapp.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.time.LocalDate;


@Route("login")
@PageTitle("AcademyTic")
public class LoginView extends HorizontalLayout {
    public LoginView(){
        setPadding(false);
        setSpacing(false);
        addClassName("login");
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(buildPortada(), buildFormLogin());
    }

    private Component buildFormLogin() {
        VerticalLayout FormLogin = new VerticalLayout();
        H1 title = new H1("Iniciar Sesión");

        H3 nombre = new H3("Bienvenido USUARIO");
        nombre.addClassName("login-form-letra");

        TextField usuario = new TextField("Ingrese su nombre de usuario:");
        usuario.setPlaceholder("Nombre de usuario...");
        usuario.setWidthFull();
        usuario.setPrefixComponent(new Icon(VaadinIcon.USER));

        PasswordField clave = new PasswordField("Ingrese su contraseña:");
        clave.setPlaceholder("Contraseña...");
        clave.setWidthFull();
        clave.setPrefixComponent(new Icon(VaadinIcon.LOCK));

        Button next = new Button("Siguiente");
        next.setWidthFull();
        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));

        Button next2 = new Button("Siguiente");
        next2.setWidthFull();
        next2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next2.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));

        FormLogin.add(title,usuario,next);
        FormLogin.setAlignItems(Alignment.CENTER);

        next.addClickListener(e->{
            FormLogin.removeAll();
            FormLogin.add(title, nombre, clave, next2 );
        });
        return FormLogin;
    }

    private Component buildPortada() {
        VerticalLayout Portada = new VerticalLayout();

        Portada.setPadding(false);
        Portada.setSizeFull();
        Portada.setAlignItems(Alignment.CENTER);
        Portada.addClassName("login-portada");

        Image logo = new Image("img/escudo.png","escudo");
        logo.addClassName("login-logo");

        H2 title = new H2("AcademyTics");
        title.addClassName("login-portada-letra");

        H4 title2 = new H4("Sistema de gestión académico y administrativo" );
        title2.addClassName("login-portada-letra");

        H5 title3 = new H5("Derechos reservados Unidad Educativa Julia Navarrete Mendoza " + LocalDate.now().getYear());
        title3.addClassName("login-portada-letra");

        Portada.add(logo, title, title2, title3);
        Portada.setJustifyContentMode(JustifyContentMode.CENTER);
        return Portada;
    }


}
