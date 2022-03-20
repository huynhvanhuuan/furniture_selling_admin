package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private long id;
    private String name;
    private String size;
    private String description;
    private Trademark trademark;
    private Category category;
    private Date dateCreated;
    private Date lastUpdated;
    private boolean active;
    private List<Warehouse> products;

    public Product() {
    }
    
    public Product(long id, String name, String size, String description, Trademark trademark, Category category,
                   Date dateCreated, Date lastUpdated, boolean active) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.description = description;
        this.category = category;
        this.trademark = trademark;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.active = active;
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
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Trademark getTrademark() {
        return trademark;
    }

    public void setTrademark(Trademark trademark) {
        this.trademark = trademark;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public List<Warehouse> getProducts() {
        return products;
    }
    
    public void setProducts(List<Warehouse> products) {
        this.products = products;
    }

}
