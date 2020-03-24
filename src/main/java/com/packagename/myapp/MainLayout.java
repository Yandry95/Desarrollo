package com.packagename.myapp;

import com.packagename.myapp.Views.Historial_Medico;
import com.packagename.myapp.Views.Inscripcion;
import com.packagename.myapp.Views.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.Optional;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    Tabs tabs;
    MenuItem salir;
    String nombreUsuario = VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA").toString();

    public MainLayout() {
        buildHead();
        buildDrawer();
    }

    private void buildHead() {
        Image escudo = new Image("img/escudo.png","escudo");
        escudo.addClassName("escudo");
        H1 logo = new H1("UE JUNAME");
        logo.addClassName("logo");
        DrawerToggle draw = new DrawerToggle();
        draw.addClassName("draw");
        HorizontalLayout head = new HorizontalLayout(draw, escudo, logo);
        head.addClassName("header");
        head.setWidth("100%");
        head.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(head);
    }

    private void buildDrawer() {
        VerticalLayout pizarra = new VerticalLayout();

        Image image = new Image("/img/user.jpg","Image");
        image.addClassName("logo-drawer");

        RouterLink inscripcionlink = new RouterLink("Inscripción", Inscripcion.class);
        inscripcionlink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink historiallink = new RouterLink("Historial Médico", Historial_Medico.class);
        historiallink.setHighlightCondition(HighlightConditions.sameLocation());

        tabs = new Tabs();
        Tab tab1 = new Tab(inscripcionlink);
        tab1.addComponentAsFirst(new Icon(VaadinIcon.EDIT));
        Tab tab2 = new Tab(historiallink);
        tab2.addComponentAsFirst(new Icon(VaadinIcon.DOCTOR));
        tabs.add(tab1, tab2);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);


        pizarra.add(image,buildUserMenu(), tabs);
        pizarra.addClassName("pizarra");
        pizarra.setAlignItems(FlexComponent.Alignment.CENTER);
        addToDrawer(pizarra);
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addThemeVariants(MenuBarVariant.LUMO_SMALL, MenuBarVariant.LUMO_TERTIARY);
        MenuItem usuario = settings.addItem(nombreUsuario);
        SubMenu usuarioSubMenu = usuario.getSubMenu();
        MenuItem editar = usuarioSubMenu.addItem("Editar Usuario");
        MenuItem preferencia = usuarioSubMenu.addItem("Preferencias");
        usuario.getSubMenu().add(new Hr());
        salir = usuarioSubMenu.addItem("Cerrar Sesión");
        return settings;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = tabs.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> tabs.setSelectedTab((Tab) tab));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGIN")!=null) {
            nombreUsuario = VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA").toString();
            salir.addClickListener(e->{
                UI.getCurrent().getPage().setLocation(
                        "login");
                UI.getCurrent().getSession().close();
            });
        } else {
            beforeEnterEvent.forwardTo(LoginView.class);
        }
    }

}
