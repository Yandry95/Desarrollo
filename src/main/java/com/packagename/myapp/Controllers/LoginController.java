package com.packagename.myapp.Controllers;

import com.packagename.myapp.Models.Personas;
import com.packagename.myapp.Services.JPAService;
import com.vaadin.flow.server.VaadinSession;

import javax.persistence.Query;

public class LoginController {
    public static Personas u;
    public static Query query;
    public static long contador = 0;
    public static String clave;

    public static long contar_registros(){
        JPAService.runInTransaction(em -> {
            query = em.createQuery("SELECT COUNT(u) from Personas u");
            contador = (long) (query.getSingleResult());
            return null;
        });
        return contador;
    }

    public static String login(String usuario) {
        clave = null;
        JPAService.runInTransaction(re -> {
            query = re.createQuery("SELECT u from Personas u where u.nombre_usuario = ?1");
            query.setParameter(1, usuario);
            if (query.getResultList().size() > 0) {
                u = (Personas) query.getSingleResult();
                VaadinSession.getCurrent().setAttribute("NOMBRE_PERSONA", u.getNombre()+" "+u.getApellido());
                clave = u.getClave();
            }
            return null;
        });
        return clave;
    }
}
