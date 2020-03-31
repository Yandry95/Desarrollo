package com.packagename.myapp.Views.Windows;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.Models.Personas;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Profile extends Dialog {

    public Profile() {
        setHeight("580px");
        setWidth("600px");

        VerticalLayout contenido = new VerticalLayout();
        contenido.setSpacing(false);
        contenido.setMargin(false);
        contenido.setPadding(false);
        add(contenido);

        Tab tab1 = new Tab("Editar Usuario");
        tab1.addComponentAsFirst(new Icon(VaadinIcon.USER_CHECK));
        Tab tab2 = new Tab("Preferencia");
        tab2.addComponentAsFirst(new Icon(VaadinIcon.COGS));

        Component component1 = buildEditUser();
        component1.setVisible(true);
        Component component2 = buildPreferences();
        component2.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, component1);
        tabsToPages.put(tab2, component2);

        Tabs tabs = new Tabs(tab1, tab2);
        tabs.addThemeVariants(TabsVariant.LUMO_ICON_ON_TOP,  TabsVariant.LUMO_CENTERED);
        tabs.setWidthFull();
        Div components = new Div(component1,component2);

        Set<Component> pagesShown = Stream.of(component1)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        contenido.add(tabs, components);
        contenido.setAlignItems(FlexComponent.Alignment.STRETCH);
        contenido.setHeightFull();
        contenido.expand(components);

    }

    private Component buildEditUser() {
        Personas u = PersonasController.findById((long)(VaadinSession.getCurrent().getAttribute("ID_PERSONA")));
        Editar_User editUser = new Editar_User(u);
        return editUser;
    }

    private Component buildPreferences() {
        HorizontalLayout Preferences = new HorizontalLayout();
        Preferences.setSizeFull();
        Preferences.setPadding(false);
        Preferences.add(new TextField("HOLA"));
        return Preferences;
    }
}
