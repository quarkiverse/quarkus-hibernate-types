package io.quarkiverse.hibernate.types.json;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import io.quarkiverse.hibernate.types.json.impl.JsonBinarySqlTypeDescriptor;
import io.quarkiverse.hibernate.types.json.impl.JsonTypeDescriptor;

/**
 * Maps any given Java object on a JSON column type that is managed via
 * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level.
 * <p>
 * If you are using <strong>PostgreSQL</strong>, you should use this {@link JsonBinaryType} to map both
 * <strong>{@code jsonb}</strong> and <strong>{@code json}</strong> column types.
 */
public class JsonBinaryType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    public JsonBinaryType() {
        super(JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    public String getName() {
        return JsonTypes.JSON_BIN;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
