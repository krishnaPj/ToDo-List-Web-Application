package net.agmsolutions.app;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {
	// private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PersistenceUtility.initPersistence();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		PersistenceUtility.destroy();
	}
}