package de.hilling.junit.cdi.microprofile;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/app")
public interface ControllerService {
    @GET
    @Path("/propertyString")
    @Produces(MediaType.TEXT_PLAIN)
    String getStringProperty();

    @GET
    @Path("/propertyInteger")
    @Produces(MediaType.TEXT_PLAIN)
    int getIntegerProperty();

    @GET
    @Path("/propertyBoolean")
    @Produces(MediaType.TEXT_PLAIN)
    boolean getBoolProperty();

    @GET
    @Path("/propertyLong")
    @Produces(MediaType.TEXT_PLAIN)
    long getLongProperty();
}
