package io.quarkiverse.hibernate.types.it.jackson.postgresql;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tests")
public class TestRestController {

    @Inject
    EntityManager em;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        MyEntity entity = em.find(MyEntity.class, id);
        return Response.ok(entity).build();
    }

}
