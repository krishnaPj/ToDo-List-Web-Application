package net.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

@WebListener
public class StartupListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);
	private Thread userCleanerThread;
	private ExpiredUsersWorker euw;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Executing application init");
		PersistenceUtility.initPersistence();
		this.euw = new ExpiredUsersWorker(600000l); // every 10 minutes
		this.userCleanerThread = new Thread(euw, "Expired users thread");
		this.userCleanerThread.setDaemon(true);
		this.userCleanerThread.start();
		LOGGER.info("Application init complete");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		euw.setKeepRunning(false);
		try { this.userCleanerThread.join(); } 
		catch (InterruptedException e) { LOGGER.error("Error with cleaner thread", e); }
		PersistenceUtility.destroy();
		LOGGER.info("Application destroyed");
	}
}
