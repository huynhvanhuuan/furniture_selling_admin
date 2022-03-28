package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.Warehouse;

import java.util.List;

public interface WarehouseDAO extends BaseDAO<Warehouse> {
	List<Warehouse> findByProductId(Long id);
	List<Warehouse> findByProductSku(String sku);
}
