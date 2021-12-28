package com.kcosic.jwp.shared.dal;

import com.kcosic.jwp.shared.model.entities.BaseEntity;
import com.kcosic.jwp.shared.singletons.Database;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        return entityManager.createQuery(criteriaQuery.select(from));
    }

    /**
     * Retrieve all table data without relation data
     * @param clazz Entity class that is queried
     * @param <T> Type of BaseEntity
     * @return Stream of the given class
     */
    public <T extends BaseEntity> Stream<T> retrieveAll(Class<T> clazz){
        var query = createQuery(clazz);
        return query.getResultStream();
    }

    public <T extends BaseEntity> T retrieveById(Class<T> clazz, int id){
        return entityManager.find(clazz, id, getHints(clazz));
    }

    private <T extends BaseEntity> Map<String, Object> getHints(Class<T> clazz) {
        var graph = entityManager.getEntityGraph(getEntityGraphName(clazz));
        HashMap<String, Object> map = new HashMap<>();
        map.put("jakarta.persistence.loadgraph", graph);
        return map;
    }

    private <T extends BaseEntity> String getEntityGraphName(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance().getGraphName();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends BaseEntity> T create(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(object);
        entityTransaction.commit();
        return object;
    }

    public <T extends BaseEntity> T update(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(object);
        entityTransaction.commit();
        return object;
    }

    public <T extends BaseEntity> T delete(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(object);
        entityTransaction.commit();
        return object;
    }
}
