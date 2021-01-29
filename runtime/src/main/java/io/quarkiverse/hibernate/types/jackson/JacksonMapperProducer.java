package io.quarkiverse.hibernate.types.jackson;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkiverse.hibernate.types.json.JsonMapper;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

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
