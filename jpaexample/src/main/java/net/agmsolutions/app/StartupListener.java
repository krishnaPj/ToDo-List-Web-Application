package net.agmsolutions.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		LOGGER.info("Init persistence context");
		
		PersistenceUtility.initPersistence();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		LOGGER.info("Close persistence context");
		
		PersistenceUtility.destroy();
	}
}