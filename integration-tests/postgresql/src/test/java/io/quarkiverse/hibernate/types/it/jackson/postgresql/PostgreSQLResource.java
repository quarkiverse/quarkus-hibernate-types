package io.quarkiverse.hibernate.types.it.jackson.postgresql;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class PostgreSQLResource implements QuarkusTestResourceLifecycleManager {

    PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:11.1")
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
