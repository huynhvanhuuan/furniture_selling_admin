package vn.edu.hcmuaf.fit.dto.product;

import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.entity.Product;
import vn.edu.hcmuaf.fit.entity.Trademark;
import vn.edu.hcmuaf.fit.entity.Warehouse;

import java.util.Date;
import java.util.List;

public class ProductDto {
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

    public static ProductDto createFromEntity(Product product) {

    }
}
