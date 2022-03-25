package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class Province implements Serializable {
    private long id;
    private String name;
    private String prefix;

    public Province() {
    }

    public Province(long id, String name, String prefix) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
