package com.packagename.myapp.Views;

import com.packagename.myapp.Controllers.LoginController;
import com.packagename.myapp.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Navigation;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;


@Route("login")
@PageTitle("AcademyTic")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
public class LoginView extends HorizontalLayout implements AfterNavigationObserver, BeforeEnterObserver{
    public static String clave_usuario= null;
    public static long contador;
    public static PasswordField clave;
    public static H1 title;
    public static H3 nombre;
    public static Button next2;
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
        title = new H1("Iniciar Sesión");

        nombre = new H3();
        nombre.addClassName("login-form-letra");

        TextField usuario = new TextField("Ingrese su nombre de usuario:");
        usuario.setPlaceholder("Nombre de usuario...");
        usuario.setWidthFull();
        usuario.setPrefixComponent(new Icon(VaadinIcon.USER));

        clave = new PasswordField("Ingrese su contraseña:");
        clave.setPlaceholder("Contraseña...");
        clave.setWidthFull();
        clave.setPrefixComponent(new Icon(VaadinIcon.LOCK));

        Button next = new Button("Siguiente");
        next.setWidthFull();
        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));

        next2 = new Button("Siguiente");
        next2.setWidthFull();
        next2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next2.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));

        FormLogin.add(title,usuario,next);
        FormLogin.setAlignItems(Alignment.CENTER);

        next.addClickListener(e-> {
            contador = LoginController.contar_registros();
            if (contador==0){
                VaadinSession.getCurrent().setAttribute("NOMBRE_PERSONA", "Administrador");
                VaadinSession.getCurrent().setAttribute("GENERO", "Masculino");
                if(usuario.getValue().equals("admin"))
                    continuarLogin(FormLogin);
                else
                    mensajeError("El nombre de usuario ingresado es incorrecto");
            }
            else {
                clave_usuario = LoginController.login(usuario.getValue());
                if (clave_usuario != null)
                    continuarLogin(FormLogin);
                else
                    mensajeError("El nombre de usuario ingresado es incorrecto");
            }
        });

        next2.addClickListener(e->{
            if(contador==0){
                if(clave.getValue().equals("admin"))
                    ingresar();
                else
                    mensajeError("La contraseña ingresada es incorrecta");
            }
            else{
                if(clave.getValue().equals(clave_usuario))
                    ingresar();
                else
                    mensajeError("La contraseña ingresada es incorrecta");
            }
        });

        return FormLogin;
    }

    private void ingresar() {
        VaadinSession.getCurrent().setAttribute("LOGIN", "YANDRY");
        getUI().ifPresent(e-> e.navigate("inscripcion"));
    }

    private void mensajeError(String mensaje) {
        Notification notification = new Notification(
                mensaje, 3000,
                Notification.Position.TOP_CENTER);
                notification.open();
    }

    private void continuarLogin(VerticalLayout component) {
        component.removeAll();
        String genero = (VaadinSession.getCurrent().getAttribute("GENERO").toString().equals("Masculino")) ? "Bienvenido " :
                "Bienvenida ";
        nombre.setText(genero+VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA"));
        component.add(title, nombre, clave, next2 );
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

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGIN")!=null) {
            beforeEnterEvent.forwardTo(MainLayout.class);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

    }

}
