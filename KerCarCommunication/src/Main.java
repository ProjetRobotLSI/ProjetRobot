import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        
        // Setup JMX
        MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
 
        // The WebAppContext is the entity that controls the environment in which a web application lives and
        // breathes. In this example the context path is being set to "/" so it is suitable for serving root context
        // requests and then we see it setting the location of the war. A whole host of other configurations are
        // available, ranging from configuring to support annotation scanning in the webapp (through
        // PlusConfiguration) to choosing where the webapp will unpack itself.
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        //webapp.setWar("../../jetty-distribution/target/distribution/demo-base/webapps/test.war");
        webapp.setWar("/home/barais/git/raspberry/KerCarCommunication/WebContent");
        // A WebAppContext is a ContextHandler as well so it needs to be set to the server so it is aware of where to
        // send the appropriate requests.
        server.setHandler(webapp);
 
        // Configure a LoginService
        // Since this example is for our test webapp, we need to setup a LoginService so this shows how to create a
        // very simple hashmap based one. The name of the LoginService needs to correspond to what is configured in
        // the webapp's web.xml and since it has a lifecycle of its own we register it as a bean with the Jetty
        // server object so it can be started and stopped according to the lifecycle of the server itself.
        HashLoginService loginService = new HashLoginService();
        loginService.setName("Test Realm");
        loginService.setConfig("src/test/resources/realm.properties");
        server.addBean(loginService);
 
        // Start things up! By using the server.join() the server thread will join with the current thread.
        // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
        server.start();
        server.join();
	    
	}

}
