package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.entity.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ProductDetailService {
	List<ProductDetail> getProductList() throws SQLException, ParseException;
	List<ProductDetail> getProductList(Product product) throws SQLException, ParseException;
	List<Color> getColorList() throws SQLException;
	List<Material> getMaterialList() throws SQLException;
	ProductDetail getProduct(String sku) throws SQLException, ParseException;
	Color getColor(int id) throws SQLException;
	Material getMaterial(String sku) throws SQLException;
	void create(ProductDetail productDetail) throws SQLException;
	void update(String sku, ProductDetail productDetail) throws SQLException;
	void delete(String sku) throws SQLException;
	void changeActive(String sku) throws SQLException;
}
