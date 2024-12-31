package net.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class to manage the JPA EntityManagerFactory and EntityManagers.
 */
public class PersistenceUtility {

    private static EntityManagerFactory entityManagerFactory;

    /**
     * Initializes the EntityManagerFactory using the specified persistence unit name.
     * This method should be called during application startup.
     */
    public static void initPersistence() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("ToDoList");
        }
    }

    /**
     * Closes the EntityManagerFactory to release resources.
     * This method should be called during application shutdown.
     */
    public static void destroy() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }

    /**
     * Creates and returns a new EntityManager instance.
     * This method can be used to interact with the database.
     *
     * @return a new EntityManager instance
     */
    public static EntityManager createEntityManager() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory is not initialized or already closed.");
        }
        return entityManagerFactory.createEntityManager();
    }
}