package io.quarkiverse.hibernate.types.it;

import io.quarkiverse.hibernate.types.it.entities.MyEntity;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@QuarkusTest
@QuarkusTestResource(MultipleDataSourcesResource.class)
public class MultipleDataSourcesTest {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    @ParameterizedTest
    @MethodSource
    public void shouldFindOnSource1(@NotNull String pURL, @NotNull String pEntityID) {
        Response response = RestAssured.when()
                .get(pURL + "/" + pEntityID)
                .andReturn();

        Assertions.assertEquals(200, response.statusCode());
        MyEntity entity = response.as(MyEntity.class);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(pEntityID, entity.getId());
        Assertions.assertNotNull(entity.getJsonb());
        Assertions.assertEquals(pEntityID, entity.getJsonb().getId());
        Assertions.assertEquals("test" + pEntityID, entity.getJsonb().getName());
        Assertions.assertNotNull(entity.getVarchar());
        Assertions.assertEquals(pEntityID, entity.getVarchar().getId());
        Assertions.assertEquals("test" + pEntityID, entity.getVarchar().getName());
    }

    @NotNull
    public static Stream<Arguments> shouldFindOnSource1() {
        return Stream.of(
                Arguments.of("/tests/source1", "1"),
                Arguments.of("/tests/source1", "2"),
                Arguments.of("/tests/source2", "1"),
                Arguments.of("/tests/source2", "2"));
    }

}
