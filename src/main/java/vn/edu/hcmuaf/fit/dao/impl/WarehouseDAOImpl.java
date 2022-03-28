package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.dto.product.Color;
import vn.edu.hcmuaf.fit.dto.product.Material;
import vn.edu.hcmuaf.fit.entity.Warehouse;
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

public class WarehouseDAOImpl implements WarehouseDAO {
	private final IConnectionPool connectionPool;
	private Connection connection;

	private final ProductDAO productDAO;
	
	public WarehouseDAOImpl(IConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		this.productDAO = new ProductDAOImpl(connectionPool);
	}
	
	@Override
	public List<Warehouse> getProductList() throws SQLException, ParseException {
		List<Warehouse> list = new ArrayList<>();
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
			Warehouse Warehouse = new Warehouse(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(Warehouse);
		}
		return list;
	}
	
	@Override
	public List<Warehouse> getProductList(Product product) throws SQLException, ParseException {
		List<Warehouse> list = new ArrayList<>();
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
			Warehouse Warehouse = new Warehouse(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
			list.add(Warehouse);
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
	public Warehouse getProduct(String sku) throws SQLException, ParseException {
		Warehouse Warehouse = null;
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
			Warehouse = new Warehouse(sku, product, image, color, material, unitPrice, unitInStock, discount, dateCreated, lastUpdated, active);
		}
		return Warehouse;
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
	public void create(Warehouse Warehouse) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.GET_MATERIAL);
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
	public void update(String sku, Warehouse Warehouse) throws SQLException {
		connection = connectionPool.getConnection();
		PreparedStatement statement = connection.prepareStatement(QUERY.WAREHOUSE.UPDATE);
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
