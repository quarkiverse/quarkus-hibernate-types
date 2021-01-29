package io.quarkiverse.hibernate.types.json;

import java.lang.reflect.Type;

public interface JsonMapper {

    Class<?> getBinaryTypeClass();

    <T> T fromJson(String string, Type type);

    String toJson(Object value);

    Object readObject(String value);

    Object toType(String value);

    <T> T clone(T value);
}
