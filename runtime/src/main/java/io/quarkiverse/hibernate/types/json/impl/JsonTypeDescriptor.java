package io.quarkiverse.hibernate.types.json.impl;

import java.lang.reflect.Type;
import java.util.Properties;

import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

public class JsonTypeDescriptor extends AbstractTypeDescriptor<Object> implements DynamicParameterizedType {

    private Type type;

    public JsonTypeDescriptor() {
        super(Object.class, new MutableMutabilityPlan<Object>() {
            @Override
            protected Object deepCopyNotNull(Object value) {
                return JsonMapperInstance.clone(value);
            }
        });
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final XProperty xProperty = (XProperty) parameters.get(DynamicParameterizedType.XPROPERTY);
        if (xProperty instanceof JavaXMember) {
            JavaXMember x = (JavaXMember) xProperty;
            type = x.getJavaType();
        } else {
            type = ((ParameterType) parameters.get(DynamicParameterizedType.PARAMETER_TYPE)).getReturnedClass();
        }
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        if (one instanceof String && another instanceof String) {
            return one.equals(another);
        }
        return JsonMapperInstance.areJsonEqual(one, another);
    }

    @Override
    public String toString(Object value) {
        return JsonMapperInstance.toJson(value);
    }

    @Override
    public Object fromString(String string) {
        return JsonMapperInstance.fromJson(string, type);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        if (Object.class.isAssignableFrom(type)) {
            return (X) JsonMapperInstance.toJsonType(value);
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return fromString(value.toString());
    }

}
