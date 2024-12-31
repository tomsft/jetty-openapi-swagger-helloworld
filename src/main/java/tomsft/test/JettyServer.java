package tomsft.test;

import java.net.URL;
import java.nio.file.Paths;

import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;

import io.swagger.v3.jaxrs2.integration.OpenApiServlet;

public class JettyServer {
	public static void main(String[] args) throws Exception {
		// Create a basic Jetty server object that will listen on port 8080.
		Server server = new Server(8080);

		// Create a ServletContextHandler with the context path.
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "tomsft.test");

		// Expose API definition independently into yaml/json
		ServletHolder openApi = context.addServlet(OpenApiServlet.class, "/api/*");
		openApi.setInitOrder(1);
		openApi.setInitParameter("openApi.configuration.resourcePackages", "tomsft.test");

		ServletHolder swaggerUIServlet = context.addServlet(DefaultServlet.class, "/swagger-ui/*");
		URL resource = JettyServer.class.getClassLoader().getResource("swagger-ui/");
		if (resource == null) {
			throw new IllegalStateException("Swagger-UI resources not found in classpath");
		}
		String resourceBasePath = Paths.get(resource.toURI()).toAbsolutePath().toString();
		swaggerUIServlet.setInitOrder(2);
		swaggerUIServlet.setInitParameter("resourceBase", resourceBasePath);
		swaggerUIServlet.setInitParameter("pathInfoOnly", "true");

		// Set the context handler on the server.
		server.setHandler(context);

		// Start the server.
		server.start();
		server.join();
	}
}
