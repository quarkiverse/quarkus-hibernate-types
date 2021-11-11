package io.quarkiverse.hibernate.types;

import io.quarkiverse.hibernate.types.json.JsonMapper;
import io.quarkus.arc.Unremovable;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class MyParamMapperProducer {

    @Produces
    @Singleton
    @Unremovable
    public JsonMapper jsonb() {
        return new MyParamMapper();
    }

}
