package net.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtility {
	
	private static EntityManagerFactory entityManagerFactory;
	
	public static final void initPersistence() { 
		entityManagerFactory = Persistence.createEntityManagerFactory("ToDoList"); 
	}
	
	public static final void destroy() { 
		entityManagerFactory.close(); 
	}
	
	public static final EntityManager createEntityManager() { 
		return entityManagerFactory.createEntityManager(); 
	}
}
