package io.quarkiverse.hibernate.types;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import io.quarkiverse.hibernate.types.json.JsonMapper;
import io.quarkus.arc.Unremovable;

@Singleton
public class MyParamMapperProducer {

    @Produces
    @Singleton
    @Unremovable
    public JsonMapper jsonb() {
        return new MyParamMapper();
    }

}
