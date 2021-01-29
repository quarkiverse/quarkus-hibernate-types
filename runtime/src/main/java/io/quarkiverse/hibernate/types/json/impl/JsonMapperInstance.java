package io.quarkiverse.hibernate.types.json.impl;

import java.lang.reflect.Type;

import io.quarkiverse.hibernate.types.json.JsonMapper;

/**
 * Wraps a JSON mapper {@link JsonMapper} so that you can supply your own {@link JsonMapper} reference.
 */
public class JsonMapperInstance {

    static JsonMapper JSON_MAPPER;

    static void setJsonMapper(JsonMapper jsonMapper) {
        JSON_MAPPER = jsonMapper;
    }

    public static Class<?> getBinaryTypeClass() {
        return JSON_MAPPER.getBinaryTypeClass();
    }

    public static <T> T fromJson(String string, Type type) {
        try {
            return JSON_MAPPER.fromJson(string, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object",
                    e);
        }
    }

    public static String toJson(Object value) {
        try {
            return JSON_MAPPER.toJson(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String",
                    e);
        }
    }

    public static Object readObject(String value) {
        try {
            return JSON_MAPPER.readObject(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T clone(T value) {
        try {
            return JSON_MAPPER.clone(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be clone.", e);
        }
    }

    public static boolean areJsonEqual(Object one, Object another) {
        return readObject(toJson(one)).equals(readObject(toJson(another)));
    }

    public static Object toJsonType(Object value) {
        try {
            return JSON_MAPPER.toType(JSON_MAPPER.toJson(value));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
