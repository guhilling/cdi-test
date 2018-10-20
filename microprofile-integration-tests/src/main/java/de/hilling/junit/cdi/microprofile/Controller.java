package de.hilling.junit.cdi.microprofile;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Heiko Braun
 */
@ApplicationScoped
@Path("/app")
public class Controller {

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "some.string.property")
    private String stringProperty;

    @Inject
    @ConfigProperty(name = "some.integer.property")
    private Integer intProperty;

    @Inject
    @ConfigProperty(name = "some.boolean.property")
    private Boolean boolProperty;

    @GET
    @Path("/propertyString")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStringProperty() {
        return stringProperty;
    }

    @GET
    @Path("/propertyInteger")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIntegerProperty() {
        return String.valueOf(intProperty);
    }

    @GET
    @Path("/propertyBoolean")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBoolProperty() {
        return String.valueOf(boolProperty);
    }

}
