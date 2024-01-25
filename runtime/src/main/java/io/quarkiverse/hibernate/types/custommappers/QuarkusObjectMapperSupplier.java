package io.quarkiverse.hibernate.types.custommappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier;
import io.hypersistence.utils.common.ReflectionUtils;

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

        //noinspection rawtypes add custom JsonArray Mapper
        Class jsonArrClass = ReflectionUtils.getClassOrNull("io.vertx.core.json.JsonArray");
        if (jsonArrClass != null) {
            quarkusHibernateTypesModule.addSerializer(jsonArrClass, new VertxJsonArrayMapper.Serializer());
            quarkusHibernateTypesModule.addDeserializer(jsonArrClass, new VertxJsonArrayMapper.Deserializer());
        }

        objectMapper.registerModule(quarkusHibernateTypesModule);
        return objectMapper;
    }

}
