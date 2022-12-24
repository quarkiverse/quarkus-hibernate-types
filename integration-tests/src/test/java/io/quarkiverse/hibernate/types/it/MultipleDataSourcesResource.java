package io.quarkiverse.hibernate.types.it;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class MultipleDataSourcesResource implements QuarkusTestResourceLifecycleManager {

    MySQLContainer<?> source1 = new MySQLContainer<>("mysql:5.7.22")
            .withUrlParam("useSSL", "false")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    PostgreSQLContainer<?> source2 = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @Override
    public Map<String, String> start() {
        source1.start();
        source2.start();

        Map<String, String> props = new HashMap<>();
        props.put("quarkus.datasource.\"source1\".jdbc.url", source1.getJdbcUrl());
        props.put("quarkus.datasource.\"source2\".jdbc.url", source2.getJdbcUrl());
        return props;
    }

    @Override
    public void stop() {
        source1.close();
        source2.close();
    }
}
