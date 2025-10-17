package tomsft.test;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.security.KeyStore;
import java.util.Enumeration;

/**
Simple Web Server https with certificate
*/

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        String keystorePath = "C:\\somepath\\mynewcert.pfx";
        String password = "changeit";

        // Load PKCS12 keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keyStore.load(fis, password.toCharArray());
        }

        // Print aliases
        Enumeration<String> aliases = keyStore.aliases();
        System.out.println("Loaded aliases:");
        String alias = "1";
        while (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
        	System.out.println(" - " + alias);
        }

        // SSL Context
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStore(keyStore);
        sslContextFactory.setKeyManagerPassword( password);
        sslContextFactory.setCertAlias( alias);
        sslContextFactory.setIncludeProtocols("TLSv1.2", "TLSv1.3");

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.setSecureScheme("https");
        httpsConfig.setSecurePort(8443);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // HTTPS Connector
        Server server = new Server();
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(httpsConfig));
        sslConnector.setPort(8443);
        server.addConnector(sslConnector);

        // Simple Hello World Servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder( new HttpServlet() {
        			@Override
        			protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        				resp.setContentType("text/plain");
        				resp.getWriter().println("Hello, Secure World!");
        			}
        }), "/*");

        server.setHandler(context);
        server.start();
        System.out.println("Server started at https://localhost:8443");
        server.join();
    }
}
