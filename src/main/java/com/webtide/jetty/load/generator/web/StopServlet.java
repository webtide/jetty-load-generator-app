package com.webtide.jetty.load.generator.web;

import com.webtide.jetty.load.generator.Application;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class StopServlet
    extends HttpServlet
{

    private static final Logger LOGGER = Log.getLogger( StopServlet.class );

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {

        LOGGER.info( "stop application" );

        Server server = Application.getServer();
        if ( server != null && server.isRunning() )
        {
            try
            {
                server.stop();
            }
            catch ( Exception e )
            {
                LOGGER.info( "skip cannot stop server", e );
            }
        }
        System.exit( 0 );
    }
}
