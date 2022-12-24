package io.quarkiverse.hibernate.types.it.source1;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkiverse.hibernate.types.it.entities.MyEntity;
import io.quarkus.hibernate.orm.PersistenceUnit;

@Path("/tests/source1")
public class TestRestController {

    @Inject
    @PersistenceUnit("source1")
    EntityManager em;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Object entity = em.find(MyEntity.class, id);
        return Response.ok(entity).build();
    }

}
