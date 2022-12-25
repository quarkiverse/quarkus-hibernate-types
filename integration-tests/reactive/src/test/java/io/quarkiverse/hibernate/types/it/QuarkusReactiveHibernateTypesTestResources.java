package io.quarkiverse.hibernate.types.it;

import java.util.Map;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * TestResource to load all necessary containers for reactive tests
 *
 * @author w.glanzer, 25.12.2022
 */
public class QuarkusReactiveHibernateTypesTestResources implements QuarkusTestResourceLifecycleManager {

    @SuppressWarnings("resource")
    private final JdbcDatabaseContainer<?> nameless = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("hibernate_orm_test")
            .withUsername("hibernate_orm_test")
            .withPassword("hibernate_orm_test");

    @Override
    public Map<String, String> start() {
        nameless.start();
        return Map.of("quarkus.datasource.reactive.url", "vertx-reactive:" + nameless.getJdbcUrl().substring(5));
    }

    @Override
    public void stop() {
        nameless.close();
    }

}
