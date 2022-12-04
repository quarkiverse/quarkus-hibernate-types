package io.quarkiverse.hibernate.types.it.jackson.multiple;

import org.junit.jupiter.api.*;

import io.quarkiverse.hibernate.types.it.jackson.multiple.source1.MyEntity;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(MultipleDataSourcesResource.class)
public class MultipleDataSourcesTest {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void shouldFindOnSource1() {
        Response response = RestAssured.when()
                .get("/tests/source1/1")
                .andReturn();

        Assertions.assertEquals(200, response.statusCode());
        MyEntity entity = response.as(MyEntity.class);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("1", entity.getId());
        Assertions.assertNotNull(entity.getJsonb());
        Assertions.assertEquals("1", entity.getJsonb().getId());
        Assertions.assertEquals("test1-m", entity.getJsonb().getName());
        Assertions.assertNotNull(entity.getVarchar());
        Assertions.assertEquals("1", entity.getVarchar().getId());
        Assertions.assertEquals("test1-m", entity.getVarchar().getName());
    }

    @Test
    public void shouldFindOnSource2() {
        Response response = RestAssured.when()
                .get("/tests/source2/2")
                .andReturn();

        Assertions.assertEquals(200, response.statusCode());
        MyEntity entity = response.as(MyEntity.class);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("2", entity.getId());
        Assertions.assertNotNull(entity.getJsonb());
        Assertions.assertEquals("2", entity.getJsonb().getId());
        Assertions.assertEquals("test2-p", entity.getJsonb().getName());
        Assertions.assertNotNull(entity.getVarchar());
        Assertions.assertEquals("2", entity.getVarchar().getId());
        Assertions.assertEquals("test2-p", entity.getVarchar().getName());
    }

}
