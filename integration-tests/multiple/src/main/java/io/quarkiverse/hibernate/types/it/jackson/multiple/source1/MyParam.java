package io.quarkiverse.hibernate.types.it.jackson.multiple.source1;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MyParam {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
