package io.quarkiverse.hibernate.types;

import io.quarkus.arc.Arc;
import io.quarkus.test.QuarkusUnitTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomMapperTestCase {

    @RegisterExtension
    final static QuarkusUnitTest TEST = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(MyParamMapperProducer.class, MyParamMapper.class, MyParam.class, MyEntity.class)
                    .addAsResource("application.properties")
                    .addAsResource("import-custom.sql", "import.sql"));

    @Inject
    EntityManager em;

    @Test
    public void testMetrics() {
        Arc.container().requestContext().activate();
        try {
            MyEntity entity = em.find(MyEntity.class, "1");
            assertNotNull(entity);
            assertEquals("1", entity.getId());
            assertNotNull(entity.getParam());
            assertEquals("1", entity.getParam().getId());
            assertEquals("test1", entity.getParam().getName());
            entity = em.find(MyEntity.class, "2");
            assertNotNull(entity);
            assertEquals("2", entity.getId());
            assertNotNull(entity.getParam());
            assertEquals("2", entity.getParam().getId());
            assertEquals("test2", entity.getParam().getName());
        } finally {
            Arc.container().requestContext().terminate();
        }
    }
}
