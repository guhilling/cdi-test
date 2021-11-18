package de.hilling.junit.cdi.microprofile;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

    @GET
    @Path("/propertyHorse")
    @Produces(MediaType.TEXT_PLAIN)
    String getHorseProperty();
}
