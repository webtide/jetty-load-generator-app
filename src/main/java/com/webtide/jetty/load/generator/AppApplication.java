package com.webtide.jetty.load.generator;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
public class AppApplication
{

    private static ServerConnector serverConnector;

    public static void main( String[] args )
        throws Exception
    {

        ApplicationContext applicationContext = SpringApplication.run( AppApplication.class, args );

        JettyEmbeddedServletContainerFactory jetty =
            applicationContext.getBean( JettyEmbeddedServletContainerFactory.class );

        System.out.println( "local.port:" + serverConnector.getLocalPort() );

        Path path = Paths.get( "jetty.local.port" );

        Files.write( path, //
                     Collections.singleton(Integer.toString( serverConnector.getLocalPort())), //
                     StandardOpenOption.TRUNCATE_EXISTING, //
                     StandardOpenOption.WRITE, //
                     StandardOpenOption.CREATE);
    }

    //olamy: hackhish way to get local port when starting with random

    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory()
    {

        return new JettyEmbeddedServletContainerFactory()
        {

            @Override
            public Collection<JettyServerCustomizer> getServerCustomizers()
            {
                Collection<JettyServerCustomizer> serverCustomizers = super.getServerCustomizers();

                serverCustomizers.add(
                    server -> {
                        Connector connector = server.getConnectors()[0];
                        if ( connector instanceof ServerConnector )
                        {
                            AppApplication.serverConnector = (ServerConnector) connector;
                        }
                    }
                );

                return serverCustomizers;
            }

        };

    }
}
