package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.ProductDetail;

import java.util.List;

public interface ProductDetailDAO extends BaseDAO<ProductDetail> {
	List<ProductDetail> findByProductId(Long id);
	ProductDetail findBySku(String sku);
}
