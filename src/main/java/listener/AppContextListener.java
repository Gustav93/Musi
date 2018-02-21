package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class AppContextListener
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener
{
    /**
     * Default constructor. 
     */
    public AppContextListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  
    {
    	System.out.println("BAJANDO APP");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  
    {
        System.out.println("LEVANTANDO APP");
    }
}
