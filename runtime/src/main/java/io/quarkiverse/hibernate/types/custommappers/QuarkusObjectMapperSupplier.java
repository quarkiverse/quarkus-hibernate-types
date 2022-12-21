package io.quarkiverse.hibernate.types.custommappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vladmihalcea.hibernate.type.util.ObjectMapperSupplier;
import com.vladmihalcea.hibernate.util.ReflectionUtils;

/**
 * Custom implementation of the ObjectMapperSupplier to provide custom serializers/deserializers
 *
 * @author w.glanzer, 24.12.2022
 */
public class QuarkusObjectMapperSupplier implements ObjectMapperSupplier {

    @Override
    @SuppressWarnings("unchecked")
    public ObjectMapper get() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        SimpleModule quarkusHibernateTypesModule = new SimpleModule();

        //noinspection rawtypes add custom JsonObject Mapper
        Class jsonObjClass = ReflectionUtils.getClassOrNull("io.vertx.core.json.JsonObject");
        if (jsonObjClass != null) {
            quarkusHibernateTypesModule.addSerializer(jsonObjClass, new VertxJsonObjectMapper.Serializer());
            quarkusHibernateTypesModule.addDeserializer(jsonObjClass, new VertxJsonObjectMapper.Deserializer());
        }

        objectMapper.registerModule(quarkusHibernateTypesModule);
        return objectMapper;
    }

}
