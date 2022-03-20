package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.dto.product.Color;
import vn.edu.hcmuaf.fit.dto.product.Material;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.model.ProductDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {
	private final IConnectionPool connectionPool;
	private Connection connection;
	private final ProductDAO productDAO;
	
	public WarehouseDAOImpl(IConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		this.productDAO = new ProductDAOImpl(connectionPool);
	}
	
	@Override
	public List<ProductDetail> getProductList() throws SQLException, ParseException {
		List<ProductDetail> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		ResultSet rs = connection.prepareStatement(QUERY.WAREHOUSE.GET_PRODUCT_LIST).executeQuery();
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
			ProductDetail productDetail = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(productDetail);
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
			ProductDetail productDetail = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(productDetail);
		}
		return list;
	}
	
	@Override
	public List<Color> getColorList() throws SQLException {
		List<Color> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		ResultSet rs = connection.prepareStatement(QUERY.WAREHOUSE.GET_COLOR_LIST).executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String hex = rs.getString("hex");
			Color color = new Color(id, name, hex);
			list.add(color);
		}
		return list;
	}
	
	@Override
	public List<Material> getMaterialList() throws SQLException {
		List<Material> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		ResultSet rs = connection.prepareStatement(QUERY.WAREHOUSE.GET_MATERIAL_LIST).executeQuery();
		while (rs.next()) {
			String sku = rs.getString("sku");
			String name = rs.getString("name");
			Material material = new Material(sku, name);
			list.add(material);
		}
		return list;
	}
	
	@Override
	public ProductDetail getProduct(String sku) throws SQLException, ParseException {
		ProductDetail productDetail = null;
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
			productDetail = new ProductDetail(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
		}
		return productDetail;
	}
	
	@Override
	public Color getColor(int id) throws SQLException {
		Color color = null;
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_COLOR);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		if (!rs.isBeforeFirst() && rs.getRow() == 0) {
			return null;
		}
		if (rs.next()) {
			String name = rs.getString("name");
			String hex = rs.getString("hex");
			color = new Color(id, name, hex);
		}
		connectionPool.releaseConnection(connection);
		return color;
	}
	
	@Override
	public Material getMaterial(String sku) throws SQLException {
		Material material = null;
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_MATERIAL);
		statement.setString(1, sku);
		ResultSet rs = statement.executeQuery();
		if (!rs.isBeforeFirst() && rs.getRow() == 0) {
			return null;
		}
		if (rs.next()) {
			String name = rs.getString("name");
			material = new Material(sku, name);
		}
		connectionPool.releaseConnection(connection);
		return material;
	}
	
	@Override
	public void create(ProductDetail productDetail) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_MATERIAL);
		statement.setString(1, productDetail.getSku());
		statement.setInt(2, productDetail.getProduct().getId());
		statement.setString(3, productDetail.getImage());
		statement.setInt(4, productDetail.getColor().getId());
		statement.setString(5, productDetail.getMaterial().getSku());
		statement.setLong(6, productDetail.getUnitPrice());
		statement.setInt(7, productDetail.getUnitInStock());
		statement.setInt(8, productDetail.getDiscount());
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
	
	@Override
	public void update(String sku, ProductDetail productDetail) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.UPDATE);
		statement.setString(1, productDetail.getSku());
		statement.setInt(2, productDetail.getProduct().getId());
		statement.setString(3, productDetail.getImage());
		statement.setInt(4, productDetail.getColor().getId());
		statement.setString(5, productDetail.getMaterial().getSku());
		statement.setLong(6, productDetail.getUnitPrice());
		statement.setInt(7, productDetail.getUnitInStock());
		statement.setInt(8, productDetail.getDiscount());
		statement.setString(9, sku);
		statement.setString(10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(productDetail.getDateCreated()));
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
	
	@Override
	public void delete(String sku) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.DELETE);
		statement.setString(1, sku);
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
	
	@Override
	public void changeActive(String sku) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.CHANGE_ACTIVE);
		statement.setString(1, sku);
		statement.executeUpdate();
		connectionPool.releaseConnection(connection);
	}
}
