package io.quarkiverse.hibernate.types.custommappers;

import java.io.IOException;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import io.vertx.core.json.JsonObject;

/**
 * ObjectMapper to be able to map VertX JsonObject
 *
 * @author w.glanzer, 21.12.2022
 */
abstract class VertxJsonObjectMapper {

    public static class Serializer extends JsonSerializer<JsonObject> {
        @Override
        public void serialize(JsonObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeRaw(value.encode());
        }
    }

    public static class Deserializer extends JsonDeserializer<JsonObject> {
        @Override
        public JsonObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new JsonObject(p.readValueAsTree().toString());
        }
    }

}
