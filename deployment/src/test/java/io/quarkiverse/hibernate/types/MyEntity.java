package io.quarkiverse.hibernate.types;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import io.quarkiverse.hibernate.types.json.JsonType;
import io.quarkiverse.hibernate.types.json.JsonTypes;

@TypeDef(name = JsonTypes.JSON, typeClass = JsonType.class)
@Entity
public class MyEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Type(type = JsonTypes.JSON)
    @Column(name = "PARAM", columnDefinition = "varchar(255)")
    private MyParam param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MyParam getParam() {
        return param;
    }

    public void setParam(MyParam param) {
        this.param = param;
    }
}
