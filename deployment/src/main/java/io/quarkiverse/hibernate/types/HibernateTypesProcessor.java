package io.quarkiverse.hibernate.types;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class HibernateTypesProcessor {

    private static final String FEATURE = "hibernate-types";

    @BuildStep
    FeatureBuildItem createFeatureItem() {
        return new FeatureBuildItem(FEATURE);
    }

}
