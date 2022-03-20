package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.dao.impl.WarehouseDAOImpl;
import vn.edu.hcmuaf.fit.entity.Warehouse;
import vn.edu.hcmuaf.fit.infrastructure.DbManager;
import vn.edu.hcmuaf.fit.service.WarehouseService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {
	private final WarehouseDAO warehouseDAO;
	
	public WarehouseServiceImpl() {
		this.warehouseDAO = new WarehouseDAOImpl(DbManager.connectionPool);
	}
	
	@Override
	public List<Warehouse> getProductList() throws SQLException, ParseException {
		return warehouseDAO.getProductList();
	}
	
	@Override
	public List<Warehouse> getProductList(Product product) throws SQLException, ParseException {
		return warehouseDAO.getProductList(product);
	}
	
	@Override
	public List<Color> getColorList() throws SQLException {
		return warehouseDAO.getColorList();
	}
	
	@Override
	public List<Material> getMaterialList() throws SQLException {
		return warehouseDAO.getMaterialList();
	}
	
	@Override
	public ProductDetail getProduct(String sku) throws SQLException, ParseException {
		return warehouseDAO.getProduct(sku);
	}
	
	@Override
	public Color getColor(int id) throws SQLException {
		return warehouseDAO.getColor(id);
	}
	
	@Override
	public Material getMaterial(String sku) throws SQLException {
		return warehouseDAO.getMaterial(sku);
	}
	
	@Override
	public void create(ProductDetail productDetail) throws SQLException {
		warehouseDAO.create(productDetail);
	}
	
	@Override
	public void update(String sku, ProductDetail productDetail) throws SQLException {
		warehouseDAO.update(sku, productDetail);
	}
	
	@Override
	public void delete(String sku) throws SQLException {
		warehouseDAO.delete(sku);
	}
	
	@Override
	public void changeActive(String sku) throws SQLException {
		warehouseDAO.changeActive(sku);
	}
}
