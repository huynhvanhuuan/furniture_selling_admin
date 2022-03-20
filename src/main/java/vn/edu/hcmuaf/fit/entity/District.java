package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class District implements Serializable {
    private int id;
    private String name;
    private String prefix;
    private Province province;

    public District() {
    }

    public District(int id, String name, String prefix, Province province) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
