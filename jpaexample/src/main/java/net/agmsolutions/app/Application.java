package net.agmsolutions.app;

import javax.persistence.EntityManager;
import net.agmsolutions.entities.SampleEntity;

public class Application {

	public static void main(String[] args) {
		PersistenceUtility.initPersistence();

		EntityManager em = null;
		try {
			// Get EntityManager from EntityManagerFactory
			em = PersistenceUtility.createEntityManager();

			SampleEntity se = new SampleEntity();
			se.setName("Pasquale");
			se.setSurname("Marciano");
			
			SampleEntity se2 = new SampleEntity();
			se2.setName("Khova Krishna");
			se2.setSurname("Pilato");

			// Begin transaction
			em.getTransaction().begin();
			em.persist(se); // Writes to db
			em.persist(se2); // Writes to db
			em.getTransaction().commit(); // Apply changes to db
		} catch (Exception e) {
			e.printStackTrace();

			// Check and force rollback
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

		PersistenceUtility.destroy();
	}

}
