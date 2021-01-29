package io.quarkiverse.hibernate.types.json;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import io.quarkiverse.hibernate.types.json.impl.JsonStringSqlTypeDescriptor;
import io.quarkiverse.hibernate.types.json.impl.JsonTypeDescriptor;

/**
 * Maps any given Java object on a JSON column type that is managed via
 * {@link java.sql.PreparedStatement#setString(int, String)} at JDBC Driver level.
 * <p>
 * If you are using <strong>Oracle</strong>, you should use this {@link JsonType} to map a
 * <strong>{@code VARCHAR2}</strong> column type storing JSON.
 * <p>
 * If you are using <strong>SQL Server</strong>, you should use this {@link JsonType} to map an
 * <strong>{@code NVARCHAR}</strong> column type storing JSON.
 * <p>
 * If you are using <strong>MySQL</strong>, you should use this {@link JsonType} to map the <strong>{@code json}</strong>
 * column type.
 * <p>
 * If you are using <strong>PostgreSQL</strong>, then you should <strong>NOT</strong> use this {@link JsonType}. You
 * should use {@link JsonBinaryType} instead.
 */
public class JsonType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    public JsonType() {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    @Override
    public String getName() {
        return JsonTypes.JSON;
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
