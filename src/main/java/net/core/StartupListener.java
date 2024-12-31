package net.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.Cookie;

/**
 * Listener to handle application lifecycle events such as initialization and destruction.
 */
@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    private Thread userCleanerThread;
    private ExpiredUsersWorker expiredUsersWorker;
    public static Cookie ck;

    /**
     * Called when the application context is initialized.
     * Initializes persistence and starts the thread for expired user cleanup.
     *
     * @param sce the event containing the servlet context.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Executing application initialization...");

        // Initialize persistence utility
        PersistenceUtility.initPersistence();

        // Set up and start the expired users cleanup worker
        this.expiredUsersWorker = new ExpiredUsersWorker(120_000L); // 120 seconds interval
        this.userCleanerThread = new Thread(expiredUsersWorker, "ExpiredUsersCleanerThread");
        this.userCleanerThread.setDaemon(true); // Allow JVM to exit without waiting for this thread
        this.userCleanerThread.start();

        LOGGER.info("Application initialization complete.");
    }

    /**
     * Called when the application context is destroyed.
     * Stops the expired users cleanup thread and releases resources.
     *
     * @param sce the event containing the servlet context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Executing application shutdown...");

        // Signal the worker thread to stop and wait for its termination
        expiredUsersWorker.setKeepRunning(false);
        try {
            this.userCleanerThread.join(); // Wait for the thread to finish execution
        } catch (InterruptedException interruptedException) {
            LOGGER.error("Error while stopping the expired users cleaner thread: ", interruptedException);
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        // Clean up persistence utility
        PersistenceUtility.destroy();

        LOGGER.info("Application shutdown complete.");
    }
}