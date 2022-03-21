package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.CategoryDAO;
import vn.edu.hcmuaf.fit.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    public CategoryDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String sku = rs.getString("sku");
                String name = rs.getString("name");
                boolean active = rs.getBoolean("active");
                Category category = new Category(id, sku, name, active);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return categories;
    }

    @Override
    public Category findById(Long id) {
        Category category = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.FIND_CATEGORY_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String sku = rs.getString("sku");
                String name = rs.getString("name");
                boolean active = rs.getBoolean("active");
                category = new Category(id, sku, name, active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return category;
    }

    @Override
    public void save(Category category) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(category.getId() == 0 ? QUERY.CATEGORY.CREATE : QUERY.CATEGORY.UPDATE);
            statement.setString(1, category.getSku());
            statement.setString(2, category.getName());
            if (category.getId() != 0) statement.setLong(3, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void delete(Long id) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }

//    @Override
//    public List<String> getListSkuHasProduct() throws SQLException {
//        List<String> skus = new ArrayList<>();
//        connection = connectionPool.getConnection();
//        PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.GET_LIST_SKU_HAS_PRODUCT);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()) {
//            String sku = rs.getString("category_sku");
//            skus.add(sku);
//        }
//        connectionPool.releaseConnection(connection);
//        return skus;
//    }

    @Override
    public Category findBySku(String sku) {
        Category category = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.FIND_CATEGORY_BY_SKU);
            statement.setString(1, sku);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                boolean active = rs.getBoolean("active");
                category = new Category(id, sku, name, active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return category;
    }

    @Override
    public Category findByName(String name) {
        Category category = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.CATEGORY.FIND_CATEGORY_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                long id = rs.getLong("id");
                String sku = rs.getString("sku");
                boolean active = rs.getBoolean("active");
                category = new Category(id, sku, name, active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
        return category;
    }
}
