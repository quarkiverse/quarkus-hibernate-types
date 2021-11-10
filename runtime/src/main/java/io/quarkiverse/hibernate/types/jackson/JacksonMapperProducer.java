package io.quarkiverse.hibernate.types.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkiverse.hibernate.types.json.JsonMapper;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class JacksonMapperProducer {

    @Produces
    @Singleton
    @Unremovable
    @DefaultBean
    public JsonMapper jsonb(ObjectMapper objectMapper) {
        return new JacksonMapper(objectMapper);
    }

}
