package io.quarkiverse.hibernate.types.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkiverse.hibernate.types.it.entities.MyEntity;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

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
        MyEntity entity = RestAssured.when()
                .get("tests/" + pSourceName + "/" + pEntityID)
                .andReturn()
                .as(MyEntity.class);
        assertNotNull(entity);
        assertEquals(pEntityID, entity.getId());
        assertNotNull(entity.getVarchar());
        assertEquals(pEntityID, entity.getVarchar().getId());
        assertEquals("test" + pEntityID, entity.getVarchar().getName());
        assertEquals(pEntityID, entity.getJsonb().getId());
        assertEquals("test" + pEntityID, entity.getJsonb().getName());
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
