package io.quarkiverse.hibernate.types.it.jackson.mysql;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;

import java.util.Collections;
import java.util.Map;

public class MySQLResource implements QuarkusTestResourceLifecycleManager {

    MySQLContainer<?> db = new MySQLContainer<>("mysql:5.7.22")
            .withUrlParam("useSSL", "false")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
    }

    @Override
    public void stop() {
        db.close();
    }
}
