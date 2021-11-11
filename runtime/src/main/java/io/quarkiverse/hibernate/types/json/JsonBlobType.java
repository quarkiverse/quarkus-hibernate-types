package io.quarkiverse.hibernate.types.json;

import java.sql.Blob;
import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import io.quarkiverse.hibernate.types.json.impl.JsonTypeDescriptor;

/**
 * Maps any given Java object on a JSON column type that is managed via {@link java.sql.PreparedStatement#setBlob(int, Blob)} at
 * JDBC Driver level.
 * <p>
 * If you are using Oracle, you should use this {@link JsonBlobType} to map a {@code BLOB} column type storing JSON.
 */
public class JsonBlobType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType {

    public JsonBlobType() {
        super(org.hibernate.type.descriptor.sql.BlobTypeDescriptor.DEFAULT, new JsonTypeDescriptor());
    }

    public String getName() {
        return JsonTypes.JSON_BLOB;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
