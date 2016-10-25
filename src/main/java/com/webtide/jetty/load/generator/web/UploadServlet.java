package com.webtide.jetty.load.generator.web;

import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class UploadServlet extends HttpServlet
{

    private static final Logger LOGGER = Log.getLogger( UploadServlet.class );

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {
        String answer = "GET does nothing";
        LOGGER.info( answer );
        resp.getWriter().print( answer );
    }

    @Override
    protected void doPut( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {
        Path tmp = Files.createTempFile( "loadgenerator", ".jetty");
        try(OutputStream outputStream = Files.newOutputStream( tmp ))
        {
            IO.copy( req.getInputStream(), outputStream );
        }

        System.out.println( "file size:" + tmp.toFile().length() );

    }
}
