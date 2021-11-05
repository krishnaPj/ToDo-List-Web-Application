package net.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

@WebListener
public class StartupListener implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);
	private Thread userCleanerThread;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Executing application init");
		PersistenceUtility.initPersistence();
		ExpiredUsersWorker euw = new ExpiredUsersWorker(10000l);
		this.userCleanerThread = new Thread(euw, "Expired users thread");
		this.userCleanerThread.start();	
		LOGGER.info("Application init complete");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		PersistenceUtility.destroy();
		LOGGER.info("Application destroyed");
	}
}
