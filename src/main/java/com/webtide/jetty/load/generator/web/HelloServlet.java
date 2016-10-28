package com.webtide.jetty.load.generator.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olamy on 28/10/16.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet
{

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
        throws ServletException, IOException
    {
        String who = req.getParameter( "name" );

        resp.getWriter().write( "hello " + (who == null ? "unknown" : who) );
    }
}
