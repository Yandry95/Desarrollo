package com.packagename.myapp.Views.Windows;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Delete_User extends Dialog {
    public Delete_User(Personas personas) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSizeFull();

        Paragraph paragraph = new Paragraph("Está seguro que desea eliminar a " +
                personas.getNombre()+" "+personas.getApellido()+"?");

        HorizontalLayout botones = new HorizontalLayout();
        setWidthFull();

        Button eliminar = new Button("Eliminar");
        eliminar.setIcon(new Icon(VaadinIcon.TRASH));
        eliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        eliminar.addClickListener(e-> {
            PersonasController.delete(personas);
            UI.getCurrent().getPage().reload();
        });

        Button cancelar = new Button("Cancelar");
        cancelar.setIcon(new Icon(VaadinIcon.BACKSPACE));
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelar.addClickListener(e->close());

        botones.add(cancelar, eliminar);
        botones.setAlignSelf(FlexComponent.Alignment.START, cancelar);
        botones.setAlignSelf(FlexComponent.Alignment.END, eliminar);

        layout.add(paragraph, botones);
        add(layout);
    }
}
