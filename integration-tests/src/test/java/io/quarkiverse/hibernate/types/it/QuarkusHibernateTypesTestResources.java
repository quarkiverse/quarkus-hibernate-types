package io.quarkiverse.hibernate.types.it;

import java.util.Map;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * TestResource to load all necessary containers
 *
 * @author w.glanzer, 24.12.2022
 */
public class QuarkusHibernateTypesTestResources implements QuarkusTestResourceLifecycleManager {

    @SuppressWarnings("resource")
    private final JdbcDatabaseContainer<?> nameless = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @SuppressWarnings("resource")
    private final JdbcDatabaseContainer<?> postgres = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @SuppressWarnings("resource")
    private final JdbcDatabaseContainer<?> mysql = new MySQLContainer<>("mysql:5.7.22")
            .withUrlParam("useSSL", "false")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @Override
    public Map<String, String> start() {
        nameless.start();
        postgres.start();
        mysql.start();
        return Map.of("quarkus.datasource.jdbc.url", nameless.getJdbcUrl(),
                "quarkus.datasource.\"postgresql\".jdbc.url", postgres.getJdbcUrl(),
                "quarkus.datasource.\"mysql\".jdbc.url", mysql.getJdbcUrl());
    }

    @Override
    public void stop() {
        nameless.close();
        postgres.close();
        mysql.start();
    }

}
