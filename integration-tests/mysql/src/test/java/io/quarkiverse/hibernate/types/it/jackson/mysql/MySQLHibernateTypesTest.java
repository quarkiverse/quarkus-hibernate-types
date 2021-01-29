package io.quarkiverse.hibernate.types.it.jackson.mysql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(MySQLResource.class)
public class MySQLHibernateTypesTest {

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void findTest() {
        Response response = RestAssured.when()
                .get("/tests/1")
                .andReturn();

        Assertions.assertEquals(200, response.statusCode());
        MyEntity entity = response.as(MyEntity.class);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("1", entity.getId());
        Assertions.assertNotNull(entity.getJsonb());
        Assertions.assertEquals("1", entity.getJsonb().getId());
        Assertions.assertEquals("test1", entity.getJsonb().getName());
        Assertions.assertNotNull(entity.getVarchar());
        Assertions.assertEquals("1", entity.getVarchar().getId());
        Assertions.assertEquals("test1", entity.getVarchar().getName());
    }
}
