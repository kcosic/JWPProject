package com.kcosic.jwp.shared.dal;

import com.kcosic.jwp.shared.model.entities.BaseEntity;
import com.kcosic.jwp.shared.singletons.Database;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class Dal {
    private static final EntityManager entityManager= Database.getInstance();
    private static final CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();

    private Dal(){}

    private static <T extends BaseEntity> TypedQuery<T> createQuery(Class<T> clazz){
        var criteriaQuery = criteriaBuilder.createQuery(clazz);
        var from = criteriaQuery.from(clazz);
        criteriaQuery.select(from);
        return entityManager.createQuery(criteriaQuery);
    }

    public static <T extends BaseEntity> List<T> retrieveAll(Class<T> clazz){
        var query = createQuery(clazz);
        return query.getResultList();
    }

    public static <T extends BaseEntity> T retrieveById(Class<T> clazz, int id){
        return entityManager.find(clazz, id);
    }

    public static <T extends BaseEntity> T create(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        return object;
    }

    public static <T extends BaseEntity> T update(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
        return object;
    }

    public static <T extends BaseEntity> T delete(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
        return object;
    }
}
