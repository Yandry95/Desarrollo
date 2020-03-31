package com.packagename.myapp;

import com.packagename.myapp.Controllers.PersonasController;
import com.packagename.myapp.Models.Personas;
import com.packagename.myapp.Views.Historial_Medico;
import com.packagename.myapp.Views.Inscripcion;
import com.packagename.myapp.Views.LoginView;
import com.packagename.myapp.Views.Windows.Profile;
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
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    Tabs tabs;
    MenuItem salir;
    String nombreUsuario;
    Personas u;
    Image image = new Image();

    public MainLayout() {
        if(VaadinSession.getCurrent().getAttribute("ID_PERSONA")!=null){
            u = PersonasController.findById((long)(VaadinSession.getCurrent().getAttribute("ID_PERSONA")));
            nombreUsuario = u.getNombre() +" "+u.getApellido();
            byte[] imageBytes = u.getImagen();
            if(imageBytes != null) {
                StreamResource resource = new StreamResource("Image", () -> {
                    return new ByteArrayInputStream(imageBytes);
                });
                image.setSrc(resource);
            }
            else
                if(u.getSexo().equals("Masculino"))
                    image.setSrc("/img/user.jpg");
                else
                    image.setSrc("/img/user2.jpg");
        }
        else{
            image.setSrc("/img/user.jpg");
            nombreUsuario = "Administrador";
        }
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

        pizarra.add(buildUserMenu(), tabs);
        pizarra.addClassName("pizarra");
        pizarra.setAlignItems(FlexComponent.Alignment.CENTER);
        addToDrawer(pizarra);
    }

    private Component buildUserMenu() {
        VerticalLayout userAndLogo = new VerticalLayout();
        userAndLogo.setSpacing(false);

        image.addClassName("logo-drawer");

        final MenuBar settings = new MenuBar();
        settings.addThemeVariants(MenuBarVariant.LUMO_SMALL, MenuBarVariant.LUMO_TERTIARY);
        MenuItem usuario = settings.addItem(nombreUsuario);
        SubMenu usuarioSubMenu = usuario.getSubMenu();
        MenuItem editar = usuarioSubMenu.addItem("Editar Usuario");
        MenuItem preferencia = usuarioSubMenu.addItem("Preferencias");
        usuario.getSubMenu().add(new Hr());
        salir = usuarioSubMenu.addItem("Cerrar Sesión");
        editar.addClickListener(e-> {
            if (VaadinSession.getCurrent().getAttribute("ID_PERSONA")!=null) {
                Profile dialog = new Profile();
                dialog.open();
            }
        });
        preferencia.addClickListener(e-> {
            if (VaadinSession.getCurrent().getAttribute("ID_PERSONA")!=null){
                Profile dialog = new Profile();
                dialog.open();
            }
        });

        userAndLogo.add(image, settings);
        userAndLogo.setAlignItems(FlexComponent.Alignment.CENTER);
        return userAndLogo;
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
            salir.addClickListener(e->{
                UI.getCurrent().getPage().setLocation(
                        "login");
                UI.getCurrent().getSession().close();
            });
        } else {
            VaadinSession.getCurrent().setAttribute("RutaIntentada", beforeEnterEvent.getLocation().getPath());
            beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }

}
