package net.core;

import javax.persistence.*;

public class PersistenceUtility {
	
	private static EntityManagerFactory emf;
	
	public static final void initPersistence() { emf = Persistence.createEntityManagerFactory("ToDoList"); }
	
	public static final void destroy() { emf.close(); }
	
	public static final EntityManager createEntityManager() { return emf.createEntityManager(); }
}
