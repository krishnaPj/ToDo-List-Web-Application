package net.agmsolutions.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtility {
	private static EntityManagerFactory emf;

	public static final void initPersistence() {
		emf = Persistence.createEntityManagerFactory("todo-list-table");
	}

	public static final void destroy() {
		emf.close();
	}

	public static final EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
