package io.quarkiverse.hibernate.types.jackson;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.hibernate.types.json.JsonTypes;
import io.quarkiverse.hibernate.types.json.impl.JsonObjectTypeDescriptor;
import io.quarkiverse.hibernate.types.json.impl.JsonStringSqlTypeDescriptor;

/**
 * Maps a object {@link JsonNode} object on a JSON column type that is managed via
 * {@link java.sql.PreparedStatement#setString(int, String)} at JDBC Driver level. For instance, if you are using MySQL, you
 * should be using {@link JsonNodeStringType} to map the {@code json} column type to a Jackson {@link JsonNode} object.
 */
public class JsonNodeStringType extends AbstractSingleColumnStandardBasicType<JsonNode> {

    public JsonNodeStringType() {
        super(JsonStringSqlTypeDescriptor.INSTANCE,
                new JsonObjectTypeDescriptor<JsonNode>(JsonNode.class));
    }

    @Override
    public String getName() {
        return JsonTypes.JSON_OBJECT_STRING;
    }
}
