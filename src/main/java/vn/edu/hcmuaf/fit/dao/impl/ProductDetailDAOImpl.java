package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.ProductDetailDAO;
import vn.edu.hcmuaf.fit.entity.Color;
import vn.edu.hcmuaf.fit.entity.Material;
import vn.edu.hcmuaf.fit.entity.Product;
import vn.edu.hcmuaf.fit.entity.ProductDetail;
import vn.edu.hcmuaf.fit.util.DateUtil;

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
	public List<ProductDetail> getProductList() throws SQLException, ParseException {
		List<ProductDetail> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		ResultSet rs = connection.prepareStatement(QUERY.PRODUCT_DETAIL.FIND_ALL).executeQuery();
		while (rs.next()) {
			String sku = rs.getString("sku");
			Product product = productDAO.get(rs.getInt("product_id"));
			String image = rs.getString("image");
			Color color = getColor(rs.getInt("color_id"));
			Material material = getMaterial(rs.getString("material_sku"));
			long unitPrice = rs.getLong("unit_price");
			int unitInStock = rs.getInt("unit_in_stock");
			int discount = rs.getInt("discount");
			Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
			Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
			boolean active = rs.getBoolean("active");
			ProductDetail Warehouse = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(Warehouse);
		}
		return list;
	}
	
	@Override
	public List<ProductDetail> getProductList(Product product) throws SQLException, ParseException {
		List<ProductDetail> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_PRODUCT_LIST_WITH_PRODUCT_ID);
		statement.setInt(1, product.getId());
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			String sku = rs.getString("sku");
			String image = rs.getString("image");
			Color color = getColor(rs.getInt("color_id"));
			Material material = getMaterial(rs.getString("material_sku"));
			long unitPrice = rs.getLong("unit_price");
			int unitInStock = rs.getInt("unit_in_stock");
			int discount = rs.getInt("discount");
			Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
			Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
			boolean active = rs.getBoolean("active");
			ProductDetail Warehouse = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(Warehouse);
		}
		return list;
	}
	
	@Override
	public ProductDetail getProduct(String sku) throws SQLException, ParseException {
		ProductDetail Warehouse = null;
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_PRODUCT);
		statement.setString(1, sku);
		ResultSet rs = statement.executeQuery();
		if (!rs.isBeforeFirst() && rs.getRow() == 0) {
			return null;
		}
		if (rs.next()) {
			Product product = productDAO.get(rs.getInt("product_id"));
			String image = rs.getString("image");
			Color color = getColor(rs.getInt("color_id"));
			Material material = getMaterial(rs.getString("material_sku"));
			long unitPrice = rs.getLong("unit_price");
			int unitInStock = rs.getInt("unit_in_stock");
			int discount = rs.getInt("discount");
			Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
			Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
			boolean active = rs.getBoolean("active");
			Warehouse = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
		}
		return Warehouse;
	}
	
	@Override
	public void create(ProductDetail Warehouse) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_MATERIAL);
		statement.setString(1, Warehouse.getSku());
		statement.setInt(2, Warehouse.getProduct().getId());
		statement.setString(3, Warehouse.getImage());
		statement.setInt(4, Warehouse.getColor().getId());
		statement.setString(5, Warehouse.getMaterial().getSku());
		statement.setLong(6, Warehouse.getUnitPrice());
		statement.setInt(7, Warehouse.getUnitInStock());
		statement.setInt(8, Warehouse.getDiscount());
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
	
	@Override
	public void update(String sku, ProductDetail Warehouse) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.UPDATE);
		statement.setString(1, Warehouse.getSku());
		statement.setInt(2, Warehouse.getProduct().getId());
		statement.setString(3, Warehouse.getImage());
		statement.setInt(4, Warehouse.getColor().getId());
		statement.setString(5, Warehouse.getMaterial().getSku());
		statement.setLong(6, Warehouse.getUnitPrice());
		statement.setInt(7, Warehouse.getUnitInStock());
		statement.setInt(8, Warehouse.getDiscount());
		statement.setString(9, sku);
		statement.setString(10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Warehouse.getDateCreated()));
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
	
	@Override
	public void delete(String sku) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.DELETE);
		statement.setString(1, sku);
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}

	@Override
	public List<ProductDetail> findAll() {
		List<ProductDetail> productDetails = new ArrayList<>();
		connection = connectionPool.getConnection();
		try {
			connectionPool.releaseConnection(connection);
			PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.FIND_ALL);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String sku = rs.getString("sku");
				String image = rs.getString("image");
				Color color = getColor(rs.getInt("color_id"));
				Material material = getMaterial(rs.getString("material_sku"));
				long unitPrice = rs.getLong("unit_price");
				int unitInStock = rs.getInt("unit_in_stock");
				int discount = rs.getInt("discount");
				Date dateCreated = DateUtil.toDatetime(rs.getString("date_created"));
				Date lastUpdated = DateUtil.toDatetime(rs.getString("last_updated"));
				boolean active = rs.getBoolean("active");
				ProductDetail Warehouse = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
				list.add(Warehouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return productDetails;
		}
		connectionPool.releaseConnection(connection);
		return productDetails;
	}

	@Override
	public ProductDetail findById(Long id) {
		return null;
	}

	@Override
	public void save(ProductDetail productDetail) {

	}

	@Override
	public void delete(Long id) {
		connection = connectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.DELETE);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.releaseConnection(connection);
	}

	@Override
	public List<ProductDetail> findByProductId(Long id) {
		connection = connectionPool.getConnection();
		List<ProductDetail> productDetails = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.FIND_BY_PRODUCT_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return productDetails;
		}
		connectionPool.releaseConnection(connection);
		return productDetails;
	}

	@Override
	public ProductDetail findBySku(String sku) {
		connection = connectionPool.getConnection();
		ProductDetail productDetail = null;
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.FIND_BY_SKU);
			statement.setString(1, sku);
			ResultSet resultSet = statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		connectionPool.releaseConnection(connection);
		return productDetail;
	}
}
