package io.quarkiverse.hibernate.types.custommappers;

import java.io.IOException;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import io.vertx.core.json.JsonArray;

/**
 * ObjectMapper to be able to map VertX JsonArray
 */
abstract class VertxJsonArrayMapper {

    public static class Serializer extends JsonSerializer<JsonArray> {
        @Override
        public void serialize(JsonArray value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeRaw(value.encode());
        }
    }

    public static class Deserializer extends JsonDeserializer<JsonArray> {
        @Override
        public JsonArray deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new JsonArray(p.readValueAsTree().toString());
        }
    }

}
