package io.quarkiverse.hibernate.types.it.reactive.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import io.quarkiverse.hibernate.types.json.JsonType;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.core.json.JsonObject;

@TypeDef(name = "json", typeClass = JsonType.class)
@Entity
@RegisterForReflection
public class MyEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Type(type = "json")
    @Column(name = "P_STRING", columnDefinition = "varchar(255)")
    private MyParam varchar;

    @Type(type = "json")
    @Column(name = "P_JSONB", columnDefinition = "json")
    private MyParam jsonb;

    @Type(type = "json")
    @Column(name = "P_VERTX", columnDefinition = "json")
    private JsonObject vertxObject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MyParam getVarchar() {
        return varchar;
    }

    public void setVarchar(MyParam varchar) {
        this.varchar = varchar;
    }

    public MyParam getJsonb() {
        return jsonb;
    }

    public void setJsonb(MyParam jsonb) {
        this.jsonb = jsonb;
    }

    public JsonObject getVertxObject() {
        return vertxObject;
    }

    public void setVertxObject(JsonObject vertxObject) {
        this.vertxObject = vertxObject;
    }
}
