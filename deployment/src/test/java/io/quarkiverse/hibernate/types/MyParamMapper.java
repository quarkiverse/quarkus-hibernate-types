package io.quarkiverse.hibernate.types;

import java.lang.reflect.Type;

import io.quarkiverse.hibernate.types.json.JsonMapper;

public class MyParamMapper implements JsonMapper {

    @Override
    public Class<?> getBinaryTypeClass() {
        return Object.class;
    }

    @Override
    public <T> T fromJson(String value, Type type) {
        return (T) readObject(value);
    }

    @Override
    public String toJson(Object value) {
        if (value == null) {
            return null;
        }
        MyParam p = (MyParam) value;
        return p.getId() + "#" + p.getName();
    }

    @Override
    public Object readObject(String value) {
        if (value == null) {
            return null;
        }
        String[] tmp = value.split("#");
        MyParam p = new MyParam();
        p.setId(tmp[0]);
        p.setName(tmp[1]);
        return p;
    }

    @Override
    public Object toType(String value) {
        return readObject(value);
    }

    @Override
    public <T> T clone(T value) {
        return fromJson(toJson(value), null);
    }
}
