package io.quarkus.workshop.superheroes.fight;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/fights")
@Produces(APPLICATION_JSON)
@ApplicationScoped
public class FightResource {

    @ConfigProperty(name = "process.milliseconds", defaultValue = "0")
    long tooManyMilliseconds;

    @Inject
    Logger logger;

    @Inject
    FightService service;


    @GET
    @Path("/randomfighters")
    @Timeout(50000)
    public Response getRandomFighters() {
        veryLongProcess();
        Fighters fighters = service.findRandomFighters();
        logger.debug("Get random fighters " + fighters);
        return Response.ok(fighters).build();
    }

    @GET
    public Response getAllFights() {
        List<Fight> fights = service.findAllFights();
        logger.debug("Total number of fights " + fights);
        return Response.ok(fights).build();
    }

    @GET
    @Path("/{id}")
    public Response getFight(Long id) {
        Fight fight = service.findFightById(id);
        if (fight != null) {
            logger.debug("Found fight " + fight);
            return Response.ok(fight).build();
        } else {
            logger.debug("No fight found with id " + id);
            return Response.noContent().build();
        }
    }

    @POST
    public Fight fight(@Valid Fighters fighters, UriInfo uriInfo) {
        return service.persistFight(fighters);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "Hello Fight Resource";
    }

    @POST
    @Path("/narrate")
    @Consumes(APPLICATION_JSON)
    @Produces(TEXT_PLAIN)
    public Response narrateFight(@Valid Fight fight) {
        logger.debug("Narrate the fight " + fight);
        String narration = service.narrateFight(fight);
        return Response.status(Response.Status.CREATED).entity(narration).build();
    
    }

    private void veryLongProcess() {
        try {
            Thread.sleep(tooManyMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}