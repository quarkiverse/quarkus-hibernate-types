package io.quarkiverse.hibernate.types.json.impl;

import java.io.Serializable;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

public class JsonObjectTypeDescriptor<T> extends AbstractTypeDescriptor<T> {

    public JsonObjectTypeDescriptor(Class<T> clazz) {
        super(clazz, new MutableMutabilityPlan<T>() {
            @Override
            public Serializable disassemble(T value) {
                return JsonMapperInstance.toJson(value);
            }

            @Override
            public T assemble(Serializable cached) {
                return (T) JsonMapperInstance.readObject((String) cached);
            }

            @Override
            protected T deepCopyNotNull(T value) {
                return JsonMapperInstance.clone(value);
            }
        });
    }

    @Override
    public boolean areEqual(T one, T another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        return JsonMapperInstance.areJsonEqual(one, another);
    }

    @Override
    public String toString(T value) {
        return JsonMapperInstance.toJson(value);
    }

    @Override
    public T fromString(String string) {
        return (T) JsonMapperInstance.readObject(string);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        if (JsonMapperInstance.getBinaryTypeClass().isAssignableFrom(type)) {
            return (X) JsonMapperInstance.readObject(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> T wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return fromString(value.toString());
    }

}
