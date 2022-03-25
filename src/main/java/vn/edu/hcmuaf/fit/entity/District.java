package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class District implements Serializable {
    private Long id;
    private String name;
    private String prefix;
    private Province province;

    public District() {
    }

    public District(Long id, String name, String prefix, Province province) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
