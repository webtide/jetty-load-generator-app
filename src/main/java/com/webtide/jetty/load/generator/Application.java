package com.webtide.jetty.load.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.webtide.jetty.load.generator.web.StopServlet;
import com.webtide.jetty.load.generator.web.UploadServlet;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.fcgi.server.ServerFCGIConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Application
{

    @Parameter( names = { "--port", "-p" }, description = "Target port" )
    private int port = 8080;

    @Parameter( names = { "--transport", "-t" }, description = "Type of transport: http, https, h2, h2c, fcgi" )
    private String transport = "http";


    private static Server server;

    public static void main( String[] args )
        throws Exception
    {

        Application application = new Application();

        new JCommander( application, args );

        server = new Server();

        server.addConnector( newServerConnector( server, application.transport, application.port ) );

        StatisticsHandler statisticsHandler = new StatisticsHandler();

        ServletContextHandler servletContextHandler = new ServletContextHandler();

        servletContextHandler.setContextPath( "/" );

        servletContextHandler.setBaseResource( Resource.newResource( getRootURI() ) );

        servletContextHandler.addServlet( new ServletHolder( new StatisticsServlet() ), "/stats" );

        servletContextHandler.addServlet( UploadServlet.class, "/upload" );

        servletContextHandler.addServlet( StopServlet.class, "/stop" );

        servletContextHandler.addServlet( DefaultServlet.class, "/" );

        statisticsHandler.setHandler( servletContextHandler );

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers( new Handler[]{ statisticsHandler, new DefaultHandler() } );

        server.setHandler( contexts );

        server.setDumpAfterStart( true );

        server.start();

        server.join();
    }

    public static Server getServer()
    {
        return server;
    }

    protected static ServerConnector newServerConnector( Server server, String transport, int port )
    {
        ServerConnector serverConnector = new ServerConnector( server, provideServerConnectionFactory( transport ) );
        serverConnector.setPort( port );
        return serverConnector;
    }

    protected static ConnectionFactory[] provideServerConnectionFactory( String transport )
    {

        List<ConnectionFactory> result = new ArrayList<>();
        switch ( transport )
        {
            case "http":
            {
                result.add( new HttpConnectionFactory( new HttpConfiguration() ) );
                break;
            }
            case "https":
            {
                HttpConfiguration configuration = new HttpConfiguration();
                configuration.addCustomizer( new SecureRequestCustomizer() );
                HttpConnectionFactory http = new HttpConnectionFactory( configuration );
                SslConnectionFactory ssl = new SslConnectionFactory();
                result.add( ssl );
                result.add( http );
                break;
            }
            case "h2c":
            {
                result.add( new HTTP2CServerConnectionFactory( new HttpConfiguration() ) );
                break;
            }
            case "h2":
            {
                HttpConfiguration configuration = new HttpConfiguration();
                configuration.addCustomizer( new SecureRequestCustomizer() );
                HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory( configuration );
                ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory( "h2" );
                SslConnectionFactory ssl = new SslConnectionFactory();// sslContextFactory, alpn.getProtocol() );
                result.add( ssl );
                result.add( alpn );
                result.add( h2 );
                break;
            }
            case "fcgi":
            {
                result.add( new ServerFCGIConnectionFactory( new HttpConfiguration() ) );
                break;
            }
            default:
            {
                throw new IllegalArgumentException();
            }
        }
        return result.toArray( new ConnectionFactory[result.size()] );
    }


    private static URI getRootURI()
        throws Exception
    {

        URL webRootLocation = Application.class.getResource( "/static/index.html" );
        if ( webRootLocation == null )
        {
            throw new IllegalStateException( "Unable to determine webroot URL location" );
        }

        URI webRootUri = URI.create( webRootLocation.toURI().toASCIIString().replaceFirst( "/index.html$", "/" ) );

        System.out.println( "webRootUri:" + webRootUri );

        return webRootUri;
    }


}
