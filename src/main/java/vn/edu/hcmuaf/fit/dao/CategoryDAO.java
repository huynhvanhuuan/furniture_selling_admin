package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO extends BaseDAO<Category> {
	List<Category> getList() throws SQLException;
	List<String> getListSkuHasProduct() throws SQLException;
	Category get(String sku) throws SQLException;
	void create(Category category) throws SQLException;
	void update(String sku, Category category) throws SQLException;
	void delete(String sku) throws SQLException;
	void changeActive(String sku) throws SQLException;
	boolean isExist(String sku, String name) throws SQLException;
}
