package tomsft.test;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.server.ResourceConfig;

public class SwaggerConfig extends ResourceConfig {
	public SwaggerConfig() {
		packages("tomsft.test");
		register(OpenApiResource.class);
	}
}
