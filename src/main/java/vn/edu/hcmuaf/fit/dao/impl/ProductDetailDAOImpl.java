package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.ProductDetailDAO;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.dto.product.Color;
import vn.edu.hcmuaf.fit.dto.product.Material;
import vn.edu.hcmuaf.fit.entity.ProductDetail;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.model.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetailDAOImpl implements ProductDetailDAO {
	private final IConnectionPool connectionPool;
	private Connection connection;

	private final ProductDAO productDAO;
	
	public ProductDetailDAOImpl(IConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		this.productDAO = new ProductDAOImpl(connectionPool);
	}

	@Override
	public List<ProductDetail> findAll() {
		return null;
	}

	@Override
	public ProductDetail findById(Long id) {
		return null;
	}

	@Override
	public void save(ProductDetail object) {

	}

	@Override
	public void delete(Long id) {
		connection = connectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.DELETE);
			statement.setString(1, sku);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.releaseConnection(connection);
	}

	@Override
	public List<ProductDetail> findByProductId(Long id) {
		return null;
	}

	@Override
	public ProductDetail findByProductSku(String sku) {
		return null;
	}

	@Override
	public void deleteByProductId(Long id) {

	}

	@Override
	public void deleteBySku(String sku) {

	}
}
