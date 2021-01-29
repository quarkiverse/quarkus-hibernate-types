package io.quarkiverse.hibernate.types;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;

import io.quarkiverse.hibernate.types.json.impl.JsonMapperRecorder;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class HibernateTypesProcessor {

    private static final String FEATURE = "hibernate-types";

    @BuildStep
    FeatureBuildItem createFeatureItem() {
        return new FeatureBuildItem(FEATURE);
    }

    @Record(RUNTIME_INIT)
    @BuildStep
    public void hibernateTypesInitBuildStep(JsonMapperRecorder recorder, BeanContainerBuildItem beanContainer) {
        BeanContainer container = beanContainer.getValue();
        recorder.init(container);
    }
}
