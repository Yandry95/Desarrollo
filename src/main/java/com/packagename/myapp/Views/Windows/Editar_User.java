package com.packagename.myapp.Views.Windows;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.IOUtils;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Editar_User extends HorizontalLayout {

    public static MemoryBuffer copybuffer = new MemoryBuffer();

    public Editar_User(Personas u) {

        setPadding(false);
        setSizeFull();

        VerticalLayout profile = new VerticalLayout();
        profile.setWidth("35%");

        Button button = new Button("Actualizar foto");
        button.setIcon(new Icon(VaadinIcon.UPLOAD));

        Image image = new Image();
        if(u.getImagen()==null)
            if(u.getSexo().equals("Masculino"))
                image.setSrc("/img/user.jpg");
            else
                image.setSrc("/img/user2.jpg");
        else {
            StreamResource resource = new StreamResource("Image", () -> {return new ByteArrayInputStream(u.getImagen());});
            image.setSrc(resource);
        }
        image.addClassName("edit-user-image");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setUploadButton(button);
        upload.setDropLabel(new Label("Arrastre una imagen aquí"));
        upload.addSucceededListener(event -> {
            StreamResource resource = new StreamResource("image", () -> buffer.getInputStream());
            image.setSrc(resource);
            copybuffer = buffer;
        });

        profile.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        profile.add(image, upload);

        VerticalLayout form = new VerticalLayout();
        form.setWidthFull();
        form.setMargin(false);

        TextField name = new TextField();
        name.setPlaceholder("Nombre...");
        name.setValue(u.getNombre());
        name.setPrefixComponent(new Icon(VaadinIcon.USER));

        TextField apellido = new TextField();
        apellido.setPlaceholder("Apellido...");
        apellido.setValue(u.getApellido());
        apellido.setPrefixComponent(new Icon(VaadinIcon.USER));

        EmailField email = new EmailField();
        email.setPlaceholder("Correo...");
        email.setValue(u.getCorreo());
        email.setPrefixComponent(new Icon(VaadinIcon.AT));

        DatePicker fecha_nacimiento = new DatePicker();
        fecha_nacimiento.setPlaceholder("Fecha_nacimiento...");
        fecha_nacimiento.setValue(u.getFecha_nacimiento());

        TextField telefono = new TextField();
        telefono.setPlaceholder("Teléfono...");
        telefono.setValue(u.getTelefono());
        telefono.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        CustomStringBlockFormatter.Options options = new CustomStringBlockFormatter.Options();
        options.setBlocks(3,3,4);
        new CustomStringBlockFormatter(options).extend(telefono);

        TextField direccion = new TextField();
        direccion.setPlaceholder("Dirección...");
        direccion.setValue(u.getDireccion());
        direccion.setPrefixComponent(new Icon(VaadinIcon.HOME));

        RadioButtonGroup<String> horizontal = new RadioButtonGroup<>();
        horizontal.setLabel("Sexo");
        horizontal.setItems("Masculino", "Femenino");
        horizontal.setValue(u.getSexo());

        Button guardar = new Button("Guardar");
        guardar.setIcon(new Icon(VaadinIcon.ADD_DOCK));
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        guardar.addClickListener(e->{
            u.setNombre(name.getValue());
            u.setApellido(apellido.getValue());
            u.setCorreo(email.getValue());
            u.setFecha_nacimiento(fecha_nacimiento.getValue());
            u.setTelefono(telefono.getValue());
            u.setDireccion(direccion.getValue());
            u.setSexo(horizontal.getValue());

            if(copybuffer.getInputStream()!=null){
                try {
                    u.setImagen(IOUtils.toByteArray(copybuffer.getInputStream()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            PersonasController.update(u);
            Notification notification = new Notification(
                    "Se ha guardado los cambios exitosamente", 4000,
                    Notification.Position.TOP_CENTER);
            notification.open();
            UI.getCurrent().getPage().reload();
        });

        form.add(name, apellido, email, fecha_nacimiento, telefono, direccion, horizontal, guardar);
        form.setAlignItems(FlexComponent.Alignment.STRETCH);
        form.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        add(profile, form);
    }
}
