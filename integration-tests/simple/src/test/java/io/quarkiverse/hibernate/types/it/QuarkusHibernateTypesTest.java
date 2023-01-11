package io.quarkiverse.hibernate.types.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.vertx.core.json.JsonObject;

/**
 * Tests, if all entities can be loaded correctly
 *
 * @author w.glanzer, 25.12.2022
 */
@QuarkusTest
@QuarkusTestResource(QuarkusHibernateTypesTestResources.class)
class QuarkusHibernateTypesTest {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * Tests, if all entities can be retrieved from database
     */
    @ParameterizedTest
    @MethodSource
    protected void shouldRetrieveEntity(@NotNull String pSourceName, @NotNull String pEntityID) {
        JsonObject entity = new JsonObject(RestAssured.when()
                .get("tests/" + pSourceName + "/" + pEntityID)
                .andReturn()
                .asString());

        assertNotNull(entity);
        assertEquals(pEntityID, entity.getString("id"));
        assertEquals(pEntityID, entity.getJsonObject("varchar").getString("id"));
        assertEquals("test" + pEntityID, entity.getJsonObject("varchar").getString("name"));
        assertEquals(pEntityID, entity.getJsonObject("jsonb").getString("id"));
        assertEquals("test" + pEntityID, entity.getJsonObject("jsonb").getString("name"));
        assertEquals(pEntityID, entity.getJsonObject("vertxObject").getString("id"));
        assertEquals("test" + pEntityID, entity.getJsonObject("vertxObject").getString("name"));
        assertEquals(pEntityID, entity.getJsonArray("vertxArray").getJsonObject(0).getString("id"));
        assertEquals("test" + pEntityID, entity.getJsonArray("vertxArray").getJsonObject(0).getString("name"));
    }

    /**
     * @return Parameters for {@link this#shouldRetrieveEntity(String, String)}
     */
    @NotNull
    static Stream<Arguments> shouldRetrieveEntity() {
        return Stream.of("default", "postgresql", "mysql")
                .flatMap(pSourceName -> Stream.of(
                        Arguments.of(pSourceName, "1"),
                        Arguments.of(pSourceName, "2")));
    }

}
