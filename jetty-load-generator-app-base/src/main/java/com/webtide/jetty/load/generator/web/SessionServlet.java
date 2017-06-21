package com.webtide.jetty.load.generator.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;

/**
 * simple servlet which create a session storing a single attribute
 */
@WebServlet( "/session" )
public class SessionServlet
    extends HttpServlet
{
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {
        HttpSession httpSession = req.getSession();
        String invalidate = req.getParameter( "invalidate" );
        if ( invalidate != null && invalidate.equals( "true" ) )
        {
            httpSession.invalidate();
            return;
        }
        Instant now = Instant.now();
        httpSession.setAttribute( "now", now );
        resp.getWriter().print( "attribute now get value:" + now.toString() );

    }
}
