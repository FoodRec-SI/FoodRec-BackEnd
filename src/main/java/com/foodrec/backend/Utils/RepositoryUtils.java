package com.foodrec.backend.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {
    private static EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        RepositoryUtils.entityManager = entityManager;
    }
    public static String findLastById(Class<?> entityClass, String idColumnName) {
        Assert.notNull(entityClass, "Entity class must not be null");
        Assert.notNull(idColumnName, "ID column name must not be null");
        String tableName = entityManager.getMetamodel().entity(entityClass).getName();
        String queryString = String.format("SELECT p.%s FROM %s p ORDER BY p.%s DESC LIMIT 1", idColumnName, tableName, idColumnName);
        Query query = entityManager.createQuery(queryString);
        query.setMaxResults(1);
        return (String) query.getSingleResult();
    }
}
