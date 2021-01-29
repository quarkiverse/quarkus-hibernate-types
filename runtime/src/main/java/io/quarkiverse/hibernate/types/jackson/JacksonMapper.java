package io.quarkiverse.hibernate.types.jackson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkiverse.hibernate.types.json.JsonMapper;

/**
 * Wraps a JSON mapper {@link ObjectMapper} so that you can supply your own {@link ObjectMapper} reference.
 */
public class JacksonMapper implements JsonMapper {

    private final ObjectMapper objectMapper;

    public JacksonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Class<?> getBinaryTypeClass() {
        return JsonNode.class;
    }

    public <T> T fromJson(String string, Class<T> clazz) {
        try {
            return objectMapper.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object",
                    e);
        }
    }

    public <T> T fromJson(String string, Type type) {
        try {
            return objectMapper.readValue(string, objectMapper.getTypeFactory().constructType(type));
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object",
                    e);
        }
    }

    public String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String",
                    e);
        }
    }

    public JsonNode readObject(String value) {
        try {
            return objectMapper.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JsonNode toType(String value) {
        return readObject(value);
    }

    public <T> T clone(T value) {
        try {
            return objectMapper.treeToValue(objectMapper.valueToTree(value), (Class<T>) value.getClass());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be clone.", e);
        }
    }
}
