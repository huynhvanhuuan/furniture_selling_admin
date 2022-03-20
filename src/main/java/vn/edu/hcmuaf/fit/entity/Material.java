package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class Material implements Serializable {
    private String sku;
    private String name;

    public Material() {
    }

    public Material(String sku, String name) {
        this.sku = sku;
        this.name = name;
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
}
