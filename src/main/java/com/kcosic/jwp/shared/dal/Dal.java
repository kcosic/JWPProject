package com.kcosic.jwp.shared.dal;

import com.kcosic.jwp.shared.model.entities.BaseEntity;
import com.kcosic.jwp.shared.singletons.Database;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class Dal {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public Dal(){
        entityManager= Database.getInstance();
        criteriaBuilder= entityManager.getCriteriaBuilder();
    }

    private <T extends BaseEntity> TypedQuery<T> createQuery(Class<T> clazz){
        var criteriaQuery = criteriaBuilder.createQuery(clazz);
        var from = criteriaQuery.from(clazz);
        criteriaQuery.select(from);
        return entityManager.createQuery(criteriaQuery);
    }

    public <T extends BaseEntity> List<T> retrieveAll(Class<T> clazz){
        var query = createQuery(clazz);
        return query.getResultList();
    }

    public <T extends BaseEntity> T retrieveById(Class<T> clazz, int id){
        return entityManager.find(clazz, id);
    }

    public <T extends BaseEntity> T create(Class<T> clazz, T object){
        entityManager.persist(object);
        entityManager.flush();
        return object;
    }

    public <T extends BaseEntity> T update(Class<T> clazz, T object){
        entityManager.merge(object);
        entityManager.flush();
        return object;
    }

    public <T extends BaseEntity> T delete(Class<T> clazz, T object){
        entityManager.remove(object);
        entityManager.flush();
        return object;
    }
}
