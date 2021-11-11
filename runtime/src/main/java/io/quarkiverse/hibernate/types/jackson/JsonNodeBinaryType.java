package io.quarkiverse.hibernate.types.jackson;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.hibernate.types.json.JsonTypes;
import io.quarkiverse.hibernate.types.json.impl.JsonBinarySqlTypeDescriptor;
import io.quarkiverse.hibernate.types.json.impl.JsonObjectTypeDescriptor;

/**
 * Maps a JSON {@link JsonNode} object on a JSON column type that is managed via
 * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level. For instance, if you are using PostgreSQL,
 * you should be using {@link JsonNodeBinaryType} to map both {@code jsonb} and {@code json} column types to a Jackson
 * {@link JsonNode} object.
 */
public class JsonNodeBinaryType extends AbstractSingleColumnStandardBasicType<JsonNode> {

    public JsonNodeBinaryType() {
        super(JsonBinarySqlTypeDescriptor.INSTANCE,
                new JsonObjectTypeDescriptor<JsonNode>(JsonNode.class));
    }

    public String getName() {
        return JsonTypes.JSON_OBJECT_BIN;
    }
}
