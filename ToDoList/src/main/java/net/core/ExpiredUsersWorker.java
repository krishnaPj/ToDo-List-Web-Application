package net.core;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.entities.UserStatus;

public class ExpiredUsersWorker implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredUsersWorker.class);
	private boolean keepRunning = true;
	private final long sleepTime;

	public ExpiredUsersWorker(long sleepTime) { 
		this.sleepTime = sleepTime; 
	}

	@Override
	public void run() {
		while (keepRunning) {
			try { 
				Thread.sleep(sleepTime); 
			} catch (InterruptedException interruptedException) { 
				LOGGER.warn("Interrupted thread: ", interruptedException); 
			}
			LOGGER.warn("Deleting records with unconfirmed emails");
			
			EntityManager em = null;
			em = PersistenceUtility.createEntityManager();
        	em.getTransaction().begin();
        	
        	Query query = em.createQuery("DELETE FROM User u WHERE u.userStatus = :status AND u.created < :createdParam");
        	query.setParameter("status", UserStatus.NOT_CONFIRMED);
        	Date dayBefore = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        	query.setParameter("createdParam", dayBefore);
        	
        	query.executeUpdate();
        	em.getTransaction().commit();
		}
	}

	public synchronized void setKeepRunning(boolean keepRunning) { 
		this.keepRunning = keepRunning; 
	}
}
