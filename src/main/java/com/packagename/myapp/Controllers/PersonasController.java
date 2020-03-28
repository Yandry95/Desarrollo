package com.packagename.myapp.Controllers;

import com.packagename.myapp.Models.Personas;
import com.packagename.myapp.Services.JPAService;

import javax.persistence.Query;
import java.util.List;

public class PersonasController{

    public static Query query;
    public static Personas u;
    public static void save(Personas persona){
        JPAService.runInTransaction(em ->{
            em.persist(persona);
            return null;
        });
    }

    public static void update (Personas persona) {
        JPAService.runInTransaction(em ->{
            em.merge(persona);
            return null;
        });
    }

    public static void delete (Personas persona){
        JPAService.runInTransaction(em ->{
            em.remove(persona);
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public static List<Personas> findAll() {
        return  JPAService.runInTransaction(em ->
                 em.createQuery("SELECT p from Personas p").getResultList()
        );
    }

    public static Personas findById(long id){
        JPAService.runInTransaction(em -> {
            u = em.find((Personas.class),id);
            return null;
        });
        return u;
    }

    static	Personas p;
    public static  Personas getMaxPersona() {
        JPAService.runInTransaction(em ->{
                    Query query = em.createQuery("select p from Personas p where p.idPersona = (select max(idPersona) from Personas)");

                    if(query.getResultList().size()>0) {
                        p = (Personas) query.getSingleResult();
                    }

                    return null;
                }
        );
        return p;
    }
}
