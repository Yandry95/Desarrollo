package com.packagename.myapp.Views;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.MainLayout;
import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter;
import org.vaadin.textfieldformatter.phone.PhoneI18nFieldFormatter;

import java.time.LocalDate;
import java.time.Period;

@Route(value = "inscripcion", layout = MainLayout.class)
@PageTitle("UE JUNAME | INSCRIPCIÓN")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
public class Inscripcion extends HorizontalLayout {
    Grid<Personas> grid = new Grid<>(Personas.class);
    public Inscripcion() {
        setSizeFull();
        setMargin(true);
        grid.setWidth("90%");
        grid.setHeight("100%");
        grid.removeAllColumns();
        grid.addColumn(Personas::getNombre).setHeader("Nombre");
        grid.addColumn(Personas::getApellido).setHeader("Apellido");
        grid.addColumn(Personas::getCorreo).setHeader("Correo");
        grid.addColumn(Personas::getTelefono).setHeader("Teléfono");
        grid.addColumn( e ->(
                Period.between(e.getFecha_nacimiento(),LocalDate.now()).getYears()
        )).setHeader("Edad");
        grid.addColumn(Personas::getSexo).setHeader("Sexo");
        grid.setColumnReorderingAllowed(true);
        grid.getColumns().forEach(col-> {col.setAutoWidth(true); col.setSortable(true);});
        Actualizar();
        add(buildForm(),grid);
    }

    private void Actualizar() {
        grid.setItems(PersonasController.findAll());
    }

    private Component buildForm() {
        VerticalLayout form = new VerticalLayout();
        form.setSizeUndefined();
        form.setMargin(false);
        TextField name = new TextField("Nombre:");
        name.setPrefixComponent(new Icon(VaadinIcon.USER));
        name.setWidthFull();
        TextField apellido = new TextField("Apellido:");
        apellido.setPrefixComponent(new Icon(VaadinIcon.USER));
        apellido.setWidthFull();
        EmailField email = new EmailField("Correo:");
        email.setPrefixComponent(new Icon(VaadinIcon.AT));
        email.setWidthFull();
        DatePicker fecha_nacimiento = new DatePicker("Fecha de Nacimiento:");
        fecha_nacimiento.setWidthFull();
        TextField telefono = new TextField("Teléfono:");
        telefono.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        telefono.setWidthFull();
        CustomStringBlockFormatter.Options options = new CustomStringBlockFormatter.Options();
        options.setBlocks(3,3,4);
        new CustomStringBlockFormatter(options).extend(telefono);
        RadioButtonGroup<String> horizontal = new RadioButtonGroup<>();
        horizontal.setLabel("Sexo");
        horizontal.setItems("Masculino", "Femenino");
        horizontal.setValue("Masculino");
        Button guardar = new Button("Guardar");
        guardar.setIcon(new Icon(VaadinIcon.ADD_DOCK));
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.setWidthFull();

        guardar.addClickListener(e->{PersonasController.save(new Personas(name.getValue(), apellido.getValue(), email.getValue(),
                fecha_nacimiento.getValue(), telefono.getValue(),horizontal.getValue()));
            Actualizar();
        });
        form.addClassName("centered-content");
        form.add(name, apellido, email, fecha_nacimiento, telefono, horizontal, guardar);
        return form;
    }

}
