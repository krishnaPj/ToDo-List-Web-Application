package net.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.Cookie;

@WebListener
public class StartupListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);
	private Thread userCleanerThread;
	private ExpiredUsersWorker expiredUsersWorker;
	public static Cookie ck;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Executing application init");
		PersistenceUtility.initPersistence();
		
		this.expiredUsersWorker = new ExpiredUsersWorker(120000l);
		this.userCleanerThread = new Thread(expiredUsersWorker, "Expired users thread");
		this.userCleanerThread.setDaemon(true);
		this.userCleanerThread.start();
		
		LOGGER.info("Application init complete");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		expiredUsersWorker.setKeepRunning(false);
		try { 
			this.userCleanerThread.join(); 
		} 
		catch (InterruptedException interruptedException) { 
			LOGGER.error("Error with cleaner thread: ", interruptedException); 
		}
		PersistenceUtility.destroy();
		LOGGER.info("Application destroyed");
	}
}
