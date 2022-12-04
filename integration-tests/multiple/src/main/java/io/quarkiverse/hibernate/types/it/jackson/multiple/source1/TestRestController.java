package io.quarkiverse.hibernate.types.it.jackson.multiple.source1;

import javax.inject.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

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
