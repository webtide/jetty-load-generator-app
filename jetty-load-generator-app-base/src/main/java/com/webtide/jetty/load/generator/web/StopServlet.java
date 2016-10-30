package com.webtide.jetty.load.generator.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger( StopServlet.class );

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {

        LOGGER.info( "stop application" );

        System.exit( 0 );
    }
}
