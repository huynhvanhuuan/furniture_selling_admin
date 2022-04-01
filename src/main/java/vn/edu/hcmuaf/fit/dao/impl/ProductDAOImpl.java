package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.CategoryDAO;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.ProductDetailDAO;
import vn.edu.hcmuaf.fit.dao.TrademarkDAO;
import vn.edu.hcmuaf.fit.entity.Category;
import vn.edu.hcmuaf.fit.entity.Product;
import vn.edu.hcmuaf.fit.entity.ProductDetail;
import vn.edu.hcmuaf.fit.entity.Trademark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductDAOImpl implements ProductDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private final TrademarkDAO trademarkDAO;
    private final CategoryDAO categoryDAO;
    private final ProductDetailDAO productDetailDAO;

    public ProductDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        trademarkDAO = new TrademarkDAOImpl(connectionPool);
        categoryDAO = new CategoryDAOImpl(connectionPool);
        productDetailDAO = new ProductDetailDAOImpl(connectionPool);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            ResultSet rs = connection.prepareStatement(QUERY.PRODUCT.FIND_ALL).executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String size = rs.getString("size");
                String description = rs.getString("description");
                Trademark trademark = trademarkDAO.findById(rs.getLong("trademark_id"));
                Category category = categoryDAO.findById(rs.getLong("category_id"));
                Date dateCreated = rs.getDate("date_created");
                Date lastUpdated = rs.getDate("last_updated");
                boolean active = rs.getBoolean("active");
                Set<ProductDetail> productDetails = new HashSet<>(productDetailDAO.findByProductId(id));

                Product product = new Product(id, name, size, description, trademark, category, dateCreated, lastUpdated, active, productDetails);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return products;
        }
        connectionPool.releaseConnection(connection);
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String name = rs.getString("name");
                String size = rs.getString("size");
                String description = rs.getString("description");
                Trademark trademark = trademarkDAO.findById(rs.getLong("trademark_id"));
                Category category = categoryDAO.findById(rs.getLong("category_id"));
                Date dateCreated = rs.getDate("date_created");
                Date lastUpdated = rs.getDate("last_updated");
                boolean active = rs.getBoolean("active");
                Set<ProductDetail> productDetails = new HashSet<>(productDetailDAO.findByProductId(id));

                product = new Product(id, name, size, description, trademark, category, dateCreated, lastUpdated, active, productDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return product;
    }

    @Override
    public void save(Product product) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(product.getId() == 0 ? QUERY.PRODUCT.CREATE : QUERY.PRODUCT.UPDATE);
            statement.setString(1, product.getName());
            statement.setString(2, product.getSize());
            statement.setString(3, product.getDescription());
            statement.setLong(4, product.getTrademark().getId());
            statement.setLong(5, product.getCategory().getId());
            statement.setBoolean(6, product.isActive());
            if (product.getId() != 0) statement.setLong(7, product.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }
}
