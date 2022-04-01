package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.dao.ProductDetailDAO;
import vn.edu.hcmuaf.fit.dao.WishlistDAO;
import vn.edu.hcmuaf.fit.entity.User;
import vn.edu.hcmuaf.fit.entity.ProductDetail;
import vn.edu.hcmuaf.fit.entity.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAOImpl implements WishlistDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private final UserDAO userDAO;
    private final ProductDetailDAO warehouseDAO;

    public WishlistDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.userDAO = new UserDAOImpl(connectionPool);
        this.warehouseDAO = new ProductDetailDAOImpl(connectionPool);
    }

    @Override
    public List<Wishlist> findAll() {
    }

    @Override
    public Wishlist findById(Long id) {
        return null;
    }

    @Override
    public void save(Wishlist wishlist) {

    }

    @Override
    public void delete(Long id) {

    }


    @Override
    public List<Wishlist> findByUserId(Long userId) {
        User user = userDAO.findById(userId);
        List<Wishlist> wishlist = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY.WISHLIST.FIND_BY_USER_ID);
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ProductDetail warehouse = warehouseDAO.findBySku(rs.getLong("product_sku"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return wishlist;
        }
        connectionPool.releaseConnection(connection);
        return wishlist;
    }

    @Override
    public List<Wishlist> findByProductSku(String productSku) {
        return null;
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
