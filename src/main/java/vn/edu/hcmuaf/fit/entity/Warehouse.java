package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;
import java.util.Date;

public class Warehouse implements Serializable {
    private String sku;
    private Product product;
    private String image;
    private Color color;
    private Material material;
    private long unitPrice;
    private int unitInStock;
    private int discount;
    private Date dateCreated;
    private Date lastUpdated;
    private boolean active;

    public Warehouse() {
    }

    public Warehouse(String sku, Product product, String image, Color color, Material material, long unitPrice, int unitInStock, int discount, Date dateCreated, Date lastUpdated, boolean active) {
        this.sku = sku;
        this.product = product;
        this.image = image;
        this.color = color;
        this.material = material;
        this.unitPrice = unitPrice;
        this.unitInStock = unitInStock;
        this.discount = discount;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.active = active;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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
}
