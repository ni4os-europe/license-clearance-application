package gr.uoa.di.madgik.lcapp.model.form;

import org.springframework.data.mongodb.core.mapping.Field;

public class Term {

    @Field("id")
    private String id;
    private String name;

    public Term() {
    }

    public Term(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

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
