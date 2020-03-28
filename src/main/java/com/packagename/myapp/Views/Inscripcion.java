package com.packagename.myapp.Views;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.MainLayout;
import com.packagename.myapp.Models.Personas;
import com.packagename.myapp.Views.Windows.Editar_Usuario;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter;

import java.time.LocalDate;
import java.time.Period;

@Route(value = "inscripcion", layout = MainLayout.class)
@PageTitle("UE JUNAME | INSCRIPCIÓN")
@PWA(name = "AcademyTics",
        shortName = "AcademyTics",
        description = "Sistema de Gestión Académico y Administrativo UE JUNAME",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
public class Inscripcion extends HorizontalLayout {
    Grid<Personas> grid = new Grid<>(Personas.class);
    public Inscripcion() {
        setSizeFull();
        setPadding(true);
        grid.removeAllColumns();
        grid.addColumn(e ->(e.getApellido()+" "+e.getNombre())).setHeader("Nombre");
        grid.addColumn( e ->(
                Period.between(e.getFecha_nacimiento(),LocalDate.now()).getYears()
        )).setHeader("Edad");
        grid.addColumn(Personas::getCorreo).setHeader("Correo");
        grid.addColumn(Personas::getTelefono).setHeader("Teléfono");
        grid.addColumn(Personas::getDireccion).setHeader("Dirección");
        grid.addColumn(Personas::getSexo).setHeader("Sexo");
        grid.setColumnReorderingAllowed(true);
        grid.getColumns().forEach(col-> {col.setAutoWidth(true); col.setSortable(true);});
        grid.setSizeFull();
        Actualizar();
        add(buildForm(),grid);
        setFlexGrow(50,grid);
    }

    private void Actualizar() {
        grid.setItems(PersonasController.findAll());
        grid.setItemDetailsRenderer(new ComponentRenderer<>(personas -> {return new Opciones(personas);}));
    }

    private Component buildForm() {
        VerticalLayout form = new VerticalLayout();
        form.setWidth("25%");
        form.setHeightFull();
        form.setPadding(false);
        form.setAlignItems(Alignment.STRETCH);
        form.setJustifyContentMode(JustifyContentMode.BETWEEN);

        TextField cedula = new TextField();
        cedula.setPlaceholder("Cédula...");
        cedula.setPrefixComponent(new Icon(VaadinIcon.USER_CARD));
        CustomStringBlockFormatter.Options options2 = new CustomStringBlockFormatter.Options();
        options2.setBlocks(3, 3, 3, 1);
        options2.setDelimiters(" ", " ", "-");
        options2.setNumericOnly(true);
        new CustomStringBlockFormatter(options2).extend(cedula);

        TextField name = new TextField();
        name.setPlaceholder("Nombre...");
        name.setPrefixComponent(new Icon(VaadinIcon.USER));

        TextField apellido = new TextField();
        apellido.setPlaceholder("Apellido...");
        apellido.setPrefixComponent(new Icon(VaadinIcon.USER));

        EmailField email = new EmailField();
        email.setPlaceholder("Correo...");
        email.setPrefixComponent(new Icon(VaadinIcon.AT));

        DatePicker fecha_nacimiento = new DatePicker();
        fecha_nacimiento.setPlaceholder("Fecha_nacimiento...");

        TextField telefono = new TextField();
        telefono.setPlaceholder("Teléfono...");
        telefono.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        CustomStringBlockFormatter.Options options = new CustomStringBlockFormatter.Options();
        options.setBlocks(3,3,4);
        new CustomStringBlockFormatter(options).extend(telefono);

        TextField direccion = new TextField();
        direccion.setPlaceholder("Dirección...");
        direccion.setPrefixComponent(new Icon(VaadinIcon.HOME));

        RadioButtonGroup<String> horizontal = new RadioButtonGroup<>();
        horizontal.setLabel("Sexo");
        horizontal.setItems("Masculino", "Femenino");
        horizontal.setValue("Masculino");
        Button guardar = new Button("Guardar");
        guardar.setIcon(new Icon(VaadinIcon.ADD_DOCK));
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        guardar.addClickListener(e->{
            String clave = cedula.getValue().replace(" ", "");
            clave = clave.replace("-", "");
            String nombre_usuario = name.getValue().toLowerCase().charAt(0)+ apellido.getValue().toLowerCase()+
                    clave.substring(6,10);
            PersonasController.save(new Personas(cedula.getValue(), name.getValue(), apellido.getValue(), email.getValue(),
                fecha_nacimiento.getValue(), telefono.getValue(), direccion.getValue(), horizontal.getValue(), nombre_usuario, clave, null));
            Actualizar();
        });

        form.add(cedula, name, apellido, email, fecha_nacimiento, telefono, direccion, horizontal, guardar);
        return form;
    }

}
