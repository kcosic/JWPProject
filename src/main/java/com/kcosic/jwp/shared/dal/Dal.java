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

    /**
     * Creates TypedQuery for wanted entity type
     * @param clazz
     * @param <T> Wanted entity type
     * @return Typed query of the wanted type
     */
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

    /**
     * Retrieves entity from database by its ID
     * @param clazz
     * @param id unique ID of the object
     * @param <T> Type of object that entity will be
     * @return Casted entity to wanted type.
     */
    public <T extends BaseEntity> T retrieveById(Class<T> clazz, int id){
        return entityManager.find(clazz, id, getHints(clazz));
    }

    /**
     * Returns NamedEntityGraph inside of HashMap
     * @param clazz Class which NamedEntityGraph is going to be used
     * @param <T>
     * @return Hashmap with the graph
     */
    private <T extends BaseEntity> Map<String, Object> getHints(Class<T> clazz) {
        var graph = entityManager.getEntityGraph(getEntityGraphName(clazz));
        HashMap<String, Object> map = new HashMap<>();
        map.put("jakarta.persistence.loadgraph", graph);
        return map;
    }

    /**
     * Retrieves NamedEntityGraphName from given class
     * @param clazz Class which NamedEntityGraph is going to be used
     * @param <T>
     * @return Name of the graph as String
     */
    private <T extends BaseEntity> String getEntityGraphName(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance().getGraphName();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates new object in database
     * @param clazz Type of object that is passed
     * @param object Value that is passed
     * @return Object with, hopefully, updated ID
     */
    public <T extends BaseEntity> T create(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(object);
        entityTransaction.commit();
        return object;
    }

    /**
     * Updates object in the database
     * @param clazz Type of object that is passed
     * @param object Value that is passed
     * @return Object with, hopefully, updated parameters
     */
    public <T extends BaseEntity> T update(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(object);
        entityTransaction.commit();
        return object;
    }

    /**
     * Removes object from the database
     * @param clazz Type of object that is passed
     * @param object Value that is passed
     */
    public <T extends BaseEntity> void delete(Class<T> clazz, T object){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(object);
        entityTransaction.commit();
    }
}
