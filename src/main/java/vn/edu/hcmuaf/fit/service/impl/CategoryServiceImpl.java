package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.CategoryDAO;
import vn.edu.hcmuaf.fit.dao.impl.CategoryDAOImpl;
import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.infrastructure.DbManager;
import vn.edu.hcmuaf.fit.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
	private final CategoryDAO categoryDAO;
	
	public CategoryServiceImpl() {
		this.categoryDAO = new CategoryDAOImpl(DbManager.connectionPool);
	}
	
	@Override
	public List<Category> getList() throws SQLException {
		return categoryDAO.getList();
	}
	
	@Override
	public List<String> getListSkuHasProduct() throws SQLException {
		return categoryDAO.getListSkuHasProduct();
	}
	
	@Override
	public Category get(String sku) throws SQLException {
		return categoryDAO.get(sku);
	}
	
	@Override
	public void create(Category category) throws SQLException {
		categoryDAO.create(category);
	}
	
	@Override
	public void update(String sku, Category category) throws SQLException {
		categoryDAO.update(sku, category);
	}
	
	@Override
	public void delete(String sku) throws SQLException {
		categoryDAO.delete(sku);
	}
	
	@Override
	public void changeActive(String sku) throws SQLException {
		categoryDAO.changeActive(sku);
	}
	
	@Override
	public boolean isExist(String sku, String name) throws SQLException {
		return categoryDAO.isExist(sku, name);
	}
}
