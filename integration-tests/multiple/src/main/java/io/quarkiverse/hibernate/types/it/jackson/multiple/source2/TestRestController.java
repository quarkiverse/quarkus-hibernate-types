package io.quarkiverse.hibernate.types.it.jackson.multiple.source2;

import javax.inject.*;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.quarkus.hibernate.orm.PersistenceUnit;

@Path("/tests/source2")
public class TestRestController {

    @Inject
    @PersistenceUnit("source2")
    EntityManager em;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Object entity = em.find(MyEntity.class, id);
        return Response.ok(entity).build();
    }

}
