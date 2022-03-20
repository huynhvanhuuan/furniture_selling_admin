package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private String sku;
    private String name;
    private boolean active;

    public Category() {
    }

    public Category(String sku, String name, boolean active) {
        this.sku = sku;
        this.name = name;
        this.active = active;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}
