package com.webtide.jetty.load.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.webtide.jetty.load.generator.web.UploadServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.StatisticsServlet;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URI;
import java.net.URL;

/**
 *
 */
public class Application
{

    @Parameter( names = { "--port", "-p" }, description = "Target port" )
    private int port = 8080;


    public static void main( String[] args )
        throws Exception
    {

        Application application = new Application();

        new JCommander( application, args );

        Server server = new Server( application.port );

        StatisticsHandler statisticsHandler = new StatisticsHandler();

        ServletContextHandler statsContext = new ServletContextHandler();

        statsContext.setContextPath( "/" );

        statsContext.setBaseResource( Resource.newResource( getRootURI() ) );

        statsContext.addServlet( new ServletHolder( new StatisticsServlet() ), "/stats" );

        statsContext.addServlet( UploadServlet.class, "/upload" );

        statsContext.addServlet( DefaultServlet.class, "/" );

        statisticsHandler.setHandler( statsContext );

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers( new Handler[]{ statisticsHandler, new DefaultHandler() } );

        server.setHandler( contexts );

        server.setDumpAfterStart( true );

        server.start();

        server.join();
    }

    private static URI getRootURI()
        throws Exception
    {

        URL webRootLocation = Application.class.getResource("/static/index.html");
        if (webRootLocation == null)
        {
            throw new IllegalStateException("Unable to determine webroot URL location");
        }

        URI webRootUri = URI.create(webRootLocation.toURI().toASCIIString().replaceFirst("/index.html$","/"));

        System.out.println( "webRootUri:" + webRootUri );

        return webRootUri;
    }


}
