package com.webtide.jetty.load.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.StatisticsServlet;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        ServletContextHandler statsContext = new ServletContextHandler( statisticsHandler, "/" );

        statsContext.addServlet( new ServletHolder( new StatisticsServlet() ), "/stats" );

        Path staticTmp = Files.createTempDirectory( "static" );
        extractResourceToTmp( staticTmp );

        System.out.println( "static resources extracted to " + staticTmp.toString() );

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase( staticTmp.toString() );

        ContextHandler staticHandler = new ContextHandler();
        staticHandler.setContextPath( "/" );
        staticHandler.insertHandler( resourceHandler );

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers( new Handler[]{ statsContext, staticHandler } );

        server.setHandler( contexts );

        server.start();

        server.join();
    }

    private static void extractResourceToTmp( Path staticPath )
        throws Exception
    {

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
            "static/index.html" ))
        {
            FileUtils.copyInputStreamToFile( inputStream,
                                             Paths.get( staticPath.toString(), "static/index.html" ).toFile() );
        }


    }


}
