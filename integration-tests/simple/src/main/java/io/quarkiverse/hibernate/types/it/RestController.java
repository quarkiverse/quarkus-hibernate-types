package io.quarkiverse.hibernate.types.it;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkiverse.hibernate.types.it.entities.MyEntity;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.vertx.core.json.JsonObject;

/**
 * @author w.glanzer, 25.12.2022
 */
@Path("/tests/{source}")
public class RestController {

    /**
     * Retrieves entity from the given source by the given id
     *
     * @param source name of the source to get the entity from
     * @param id id of the entity to get
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEntityByID(@PathParam("source") String source, @PathParam("id") String id) {
        // determine if we should use a named or unnamed persistence unit
        Set<Annotation> annotations = new HashSet<>();
        if (!source.equalsIgnoreCase("default"))
            annotations.add(new PersistenceUnit.PersistenceUnitLiteral(source));

        // retrieve entitymanager and its requested entity, and write value as string
        try (InstanceHandle<EntityManager> instance = Arc.container().instance(EntityManager.class,
                annotations.toArray(Annotation[]::new))) {
            return Response.ok(JsonObject.mapFrom(instance.get().find(MyEntity.class, id)).toString()).build();
        }
    }

}
