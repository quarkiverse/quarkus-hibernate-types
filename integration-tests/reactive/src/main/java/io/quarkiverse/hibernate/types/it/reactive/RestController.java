package io.quarkiverse.hibernate.types.it.reactive;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.reactive.mutiny.Mutiny;

import io.quarkiverse.hibernate.types.it.reactive.entities.MyEntity;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

/**
 * @author w.glanzer, 25.12.2022
 */
@Path("/tests/reactive")
@ApplicationScoped
public class RestController {

    @Inject
    protected Mutiny.SessionFactory sessionFactory;

    /**
     * Retrieves entity by the given id
     *
     * @param id id of the entity to get
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> getEntityByID(@PathParam("id") String id) {
        return sessionFactory.withTransaction(pSession -> pSession.find(MyEntity.class, id))
                .map(pEntity -> JsonObject.mapFrom(pEntity).toString())
                .map(pJson -> Response.ok(pJson).build());
    }

}
