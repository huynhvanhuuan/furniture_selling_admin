package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.dao.WishlistDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.dto.wishlist.Wishlist;
import vn.edu.hcmuaf.fit.model.ProductDetail;
import vn.edu.hcmuaf.fit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class WishlistDAOImpl implements WishlistDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;
    private final WarehouseDAO warehouseDAO;

    public WishlistDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.warehouseDAO = new WarehouseDAOImpl(connectionPool);
    }

    @Override
    public Wishlist getList(User user) throws SQLException, ParseException {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.WISHLIST.GET_LIST);
        statement.setString(1, user.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            ProductDetail product = warehouseDAO.getProduct(rs.getString("product_sku"));
            wishlist.addToWishlist(product);
        }
        return wishlist;
    }

    @Override
    public void add(String userId, String productSku) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.WISHLIST.ADD);
        statement.setString(1, userId);
        statement.setString(2, productSku);
        statement.executeUpdate();
    }

    @Override
    public void remove(String userId, String productSku) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.WISHLIST.REMOVE);
        statement.setString(1, userId);
        statement.setString(2, productSku);
        statement.executeUpdate();
    }

    @Override
    public void removeAll(String userId) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.WISHLIST.REMOVE_ALL);
        statement.setString(1, userId);
        statement.executeUpdate();
    }
}
