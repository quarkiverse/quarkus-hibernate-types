package io.quarkiverse.hibernate.types.it.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.hibernate.annotations.Type;

import io.quarkiverse.hibernate.types.json.JsonType;
import io.quarkiverse.hibernate.types.json.JsonTypes;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Entity
@RegisterForReflection
public class MyEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Type(JsonType.class)
    @Column(name = "P_STRING", columnDefinition = "varchar(255)")
    private MyParam varchar;

    @Type(JsonType.class)
    @Column(name = "P_JSONB", columnDefinition = JsonTypes.JSON)
    private MyParam jsonb;

    @Type(JsonType.class)
    @Column(name = "P_VERTX", columnDefinition = JsonTypes.JSON)
    private JsonObject vertxObject;

    @Type(JsonType.class)
    @Column(name = "P_VERTX_ARRAY", columnDefinition = JsonTypes.JSON)
    private JsonArray vertxArray;

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

    public JsonArray getVertxArray() {
        return vertxArray;
    }

    public void setVertxArray(JsonArray vertxArray) {
        this.vertxArray = vertxArray;
    }
}
