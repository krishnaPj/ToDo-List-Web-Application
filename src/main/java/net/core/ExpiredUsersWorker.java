package net.core;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.entities.UserStatus;

/**
 * A worker thread responsible for cleaning up expired or unconfirmed user records.
 */
public class ExpiredUsersWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredUsersWorker.class);

    private volatile boolean keepRunning = true; // Ensures thread safety for the flag
    private final long sleepTime; // Time to sleep between cleanup operations (in milliseconds)

    /**
     * Constructor for ExpiredUsersWorker.
     *
     * @param sleepTime the interval (in milliseconds) between cleanup operations
     */
    public ExpiredUsersWorker(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    /**
     * The main execution loop of the worker thread.
     * Periodically checks for and deletes expired or unconfirmed user records.
     */
    @Override
    public void run() {
        while (keepRunning) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException interruptedException) {
                LOGGER.warn("Worker thread interrupted: ", interruptedException);
            }

            LOGGER.info("Starting cleanup of expired or unconfirmed user records.");
            EntityManager em = null;
            try {
                em = PersistenceUtility.createEntityManager();
                em.getTransaction().begin();

                Query query = em.createQuery(
                        "DELETE FROM User u WHERE u.userStatus = :status AND u.created < :createdParam");
                query.setParameter("status", UserStatus.NOT_CONFIRMED);

                // Calculate the cutoff date for unconfirmed users (24 hours ago)
                Date cutoffDate = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
                query.setParameter("createdParam", cutoffDate);

                int deletedRecords = query.executeUpdate();
                em.getTransaction().commit();
                LOGGER.info("Deleted {} unconfirmed user(s) older than 24 hours.", deletedRecords);

            } catch (Exception exception) {
                LOGGER.error("Error during user cleanup operation: ", exception);
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
        LOGGER.info("Worker thread stopped.");
    }

    /**
     * Sets the flag to stop the worker thread gracefully.
     *
     * @param keepRunning false to stop the thread
     */
    public synchronized void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }
}