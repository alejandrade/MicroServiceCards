package com.gigamog;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;

public class Starter {
	
	public static final int DEFAULT_PORT = 8081;
	public static final String DEFAULT_PATH = "/NikeDemo";
		
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        
        
        //context.addFilter( new FilterHolder(new AuthFilter()), AuthFilter.filterPath,  EnumSet.allOf(DispatcherType.class));
        
 
        Server jettyServer = new Server(DEFAULT_PORT);
        
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, DEFAULT_PATH+"/*");
        jerseyServlet.setInitOrder(0);
       
        
        Map<String,String> params = new HashMap<>();
        params.put(
        		ServerProperties.PROVIDER_PACKAGES,
           "com.gigamog");
        params.put(
        		ServerProperties.PROVIDER_SCANNING_RECURSIVE,
                "true");    
                	
        jerseyServlet.setInitParameters(params);
        
        try {
            jettyServer.start();
            if(Desktop.isDesktopSupported())
            {
              Desktop.getDesktop().browse(new URI("http://localhost:"+DEFAULT_PORT+DEFAULT_PATH));
            }
            jettyServer.join();

            
        } finally {
            jettyServer.destroy();
        }
    }
	
	
//	public Starter() throws Exception {
//	
//		
//		Server server = configureServer();	        
//        server.start();
//        server.join();
//	}	
//
//	private Server configureServer() {
//		MyResourceConfig resourceConfig = new MyResourceConfig();		
//		//resourceConfig.packages(TheEndpoints.class.getPackage().getName());
//	    ///resourceConfig.getClasses().add(GsonJerseyProvider.class);
//		ServletContainer servletContainer = new ServletContainer(resourceConfig);
//		ServletHolder sh = new ServletHolder(servletContainer);                
//		Server server = new Server(DEFAULT_PORT);		
////		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
////        context.setContextPath("/");
////        context.addServlet(sh, "/*");
////        //context.addFilter( new FilterHolder(new ContainerFilter()), "/*",  EnumSet.allOf(DispatcherType.class));
////        
////		server.setHandler(context);
//		return server;
//	}
//	
//	public static void main(String[] args){
//		
//		try {
//			new Starter();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		
//
//		
//	}
}
