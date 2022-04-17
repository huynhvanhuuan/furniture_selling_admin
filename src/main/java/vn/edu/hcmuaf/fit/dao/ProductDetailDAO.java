package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.ProductDetail;

import java.util.List;

public interface ProductDetailDAO extends BaseDAO<ProductDetail> {
	List<ProductDetail> findByProductId(Long id);
	ProductDetail findByProductSku(String sku);
	void deleteByProductId(Long id);
	void deleteBySku(String sku);
}
