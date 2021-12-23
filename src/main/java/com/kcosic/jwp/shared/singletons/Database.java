package com.kcosic.jwp.shared.singletons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {
    private static EntityManager entityManager;
    private Database(){
    }

    public static EntityManager getInstance(){
       if(entityManager == null){
           createConnection();
       }
        return entityManager;
    }

    private static void createConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JWP");
        entityManager = entityManagerFactory.createEntityManager();
    }


}
