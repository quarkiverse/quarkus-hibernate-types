package io.quarkiverse.hibernate.types.json.impl;

import javax.enterprise.inject.Default;

import io.quarkiverse.hibernate.types.json.JsonMapper;
import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class JsonMapperRecorder {

    public void init(BeanContainer container) {
        JsonMapper mapper = container.instance(JsonMapper.class, Default.Literal.INSTANCE);
        if (mapper == null) {
            throw new IllegalStateException("Missing JsonMapper instance implementation");
        }
        JsonMapperInstance.setJsonMapper(mapper);
    }
}
