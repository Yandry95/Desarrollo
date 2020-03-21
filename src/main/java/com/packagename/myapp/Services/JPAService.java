package com.packagename.myapp.Services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;

public class JPAService {
    private static EntityManagerFactory factory;
    static {
        init();
    }
    public static void init() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("appAcademyTic");
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        factory.close();
    }
    public static EntityManagerFactory getFactory() {
        return factory;
    }
    public static <T> T runInTransaction(Function<EntityManager, T> function) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAService.getFactory().createEntityManager();
            entityManager.getTransaction().begin();

            T result = function.apply(entityManager);

            entityManager.getTransaction().commit();
            return result;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
