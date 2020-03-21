package com.packagename.myapp.Views;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.MainLayout;
import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "historial_medico", layout = MainLayout.class)
@PageTitle("UE JUNAME | HISTORIAL")
@CssImport("./styles/shared-styles.css")
public class Historial_Medico extends VerticalLayout {
    List<Personas> personList = new ArrayList<>();
    public Historial_Medico() {
        personList = PersonasController.findAll();
        setSizeFull();
        setMargin(false);
        for(int i=0; i<personList.size(); i++){
            add(buildPanel(i));
        }
    }

    private Component buildPanel(int i) {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setWidthFull();
        panel.setMargin(false);
        panel.addClassName("panel-historial");
        H3 persona = new H3(personList.get(i).getNombre()+" "+personList.get(i).getApellido());
        panel.add(persona);
        return panel;
    }

}
