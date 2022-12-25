package io.quarkiverse.hibernate.types.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

/**
 * Tests, if all entities can be loaded correctly
 *
 * @author w.glanzer, 25.12.2022
 */
@Disabled
@QuarkusTest
@QuarkusTestResource(QuarkusReactiveHibernateTypesTestResources.class)
class QuarkusReactiveHibernateTypesTest {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * Tests, if all entities can be retrieved from database
     */
    @ParameterizedTest
    @ValueSource(strings = { "1", "2" })
    protected void shouldRetrieveEntity(@NotNull String pEntityID) {
        given()
                .when().get("/tests/reactive/" + pEntityID)
                .then()
                .statusCode(200)
                .body("id", is(pEntityID),
                        "varchar.id", is(pEntityID),
                        "varchar.name", is("test" + pEntityID));
    }

}
