package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.CartDAO;
import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.dto.cart.CartItem;
import vn.edu.hcmuaf.fit.model.ProductDetail;
import vn.edu.hcmuaf.fit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {
	private final IConnectionPool connectionPool;
	private Connection connection;
	private final WarehouseDAO warehouseDAO;
	
	public CartDAOImpl(IConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		this.warehouseDAO = new WarehouseDAOImpl(connectionPool);
	}
	
	@Override
	public List<CartItem> getList(User user) throws SQLException, ParseException {
		List<CartItem> list = new ArrayList<>();
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.CART.GET_LIST);
		statement.setString(1, user.getId());
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			ProductDetail product = warehouseDAO.getProduct(rs.getString("product_sku"));
			int quantity = rs.getInt("quantity");
			CartItem item = new CartItem(user, product, quantity);
			list.add(item);
		}
		return list;
	}
	
	@Override
	public boolean addToCart(CartItem item) throws SQLException {
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.CART.ADD);
		statement.setString(1, item.getUser().getId());
		statement.setString(2, item.getProduct().getSku());
		statement.setInt(3, item.getQuantity());
		int row = statement.executeUpdate();
		return row > 0;
	}
	
	@Override
	public void updateQuantity(CartItem item) throws SQLException {
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.CART.UPDATE);
		statement.setInt(1, item.getQuantity());
		statement.setString(2, item.getUser().getId());
		statement.setString(3, item.getProduct().getSku());
		statement.executeUpdate();
	}
	
	@Override
	public void removeFromCart(CartItem item) throws SQLException {
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.CART.REMOVE);
		statement.setString(1, item.getUser().getId());
		statement.setString(2, item.getProduct().getSku());
		statement.executeUpdate();
	}
	
	@Override
	public void removeAllFromCart(String userId) throws SQLException {
		connection = connectionPool.getConnection();
		connectionPool.releaseConnection(connection);
		PreparedStatement statement = connection.prepareStatement(QUERY.CART.REMOVE_ALL);
		statement.setString(1, userId);
		statement.executeUpdate();
	}
}
