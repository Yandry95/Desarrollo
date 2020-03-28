package com.packagename.myapp.Views;

import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class Opciones extends HorizontalLayout {
    public Opciones(Personas personas) {
        setWidthFull();
        Image image = new Image();
        if(personas.getImagen()==null)
            image.setSrc("/img/user.jpg");
        else {
            StreamResource resource = new StreamResource("Image", () -> {return new ByteArrayInputStream(personas.getImagen());});
            image.setSrc(resource);
        }
        image.addClassName("opciones-image");

        VerticalLayout datos = new VerticalLayout();
        datos.setPadding(false);
        datos.setSpacing(false);
        datos.setWidthFull();

        H3 title = new H3("DATOS PERSONALES");

        HorizontalLayout bloques = new HorizontalLayout();
        bloques.setSizeFull();

        VerticalLayout bloque1 = new VerticalLayout();
        bloque1.setPadding(false);
        bloque1.setSpacing(false);
        bloque1.setWidthFull();
        TextField cedula = new TextField();
        cedula.setValue("Cédula: "+ personas.getCedula());
        cedula.setPrefixComponent(new Icon(VaadinIcon.USER_CARD));
        cedula.setReadOnly(true);
        TextField name = new TextField();
        name.setValue("Nombre: "+personas.getNombre() + " "+personas.getApellido());
        name.setPrefixComponent(new Icon (VaadinIcon.USER));
        name.setReadOnly(true);
        TextField fecha_nacimiento = new TextField();
        fecha_nacimiento.setValue("Fecha de Nacimiento: "+personas.getFecha_nacimiento());
        fecha_nacimiento.setPrefixComponent(new Icon(VaadinIcon.CALENDAR_USER));
        fecha_nacimiento.setReadOnly(true);
        TextField direccion = new TextField();
        direccion.setValue("Dirección: "+personas.getDireccion());
        direccion.setPrefixComponent(new Icon(VaadinIcon.HOME));
        direccion.setReadOnly(true);

        bloque1.add(title, cedula, name, fecha_nacimiento, direccion);
        bloque1.setAlignItems(Alignment.STRETCH);

        VerticalLayout bloque2 = new VerticalLayout();
        bloque2.setPadding(false);
        bloque2.setSpacing(false);
        bloque2.setWidthFull();
        TextField telefono = new TextField();
        telefono.setValue("Teléfono: "+ personas.getTelefono());
        telefono.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        telefono.setReadOnly(true);
        TextField correo = new TextField();
        correo.setValue("Correo: "+personas.getCorreo());
        correo.setPrefixComponent(new Icon (VaadinIcon.AT));
        correo.setReadOnly(true);
        TextField sexo = new TextField();
        sexo.setValue("Sexo: "+personas.getSexo());
        if(personas.getSexo().equals("Femenino"))
            sexo.setPrefixComponent(new Icon(VaadinIcon.FEMALE));
        else
            sexo.setPrefixComponent(new Icon(VaadinIcon.MALE));
        sexo.setReadOnly(true);

        bloque2.add(telefono, correo, sexo);
        bloque2.setAlignItems(Alignment.STRETCH);

        bloques.add(bloque1, bloque2);
        datos.add(title, bloques);
        datos.setAlignItems(Alignment.CENTER);

        VerticalLayout botones = new VerticalLayout();
        botones.setPadding(false);
        botones.setWidth("18%");

        Button modificar = new Button("Modificar");
        modificar.setIcon(new Icon(VaadinIcon.EDIT));
        modificar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        modificar.addClickListener(e->{});

        Button eliminar = new Button("Eliminar");
        eliminar.setIcon(new Icon(VaadinIcon.TRASH));
        eliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        eliminar.addClickListener(e->{});

        botones.add(image, modificar, eliminar);
        botones.setAlignItems(Alignment.STRETCH);
        botones.setAlignSelf(Alignment.CENTER, image);
        botones.setJustifyContentMode(JustifyContentMode.END);

        add(datos, botones);
        setFlexGrow(0,botones);
        setFlexGrow(50, datos);
    }
}
