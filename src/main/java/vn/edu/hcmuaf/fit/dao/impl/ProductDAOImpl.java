package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.CategoryDAO;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.dao.TrademarkDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.model.Category;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.model.Trademark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductDAOImpl implements ProductDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;
    private TrademarkDAO trademarkDAO;
    private CategoryDAO categoryDAO;

    public ProductDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        trademarkDAO = new TrademarkDAOImpl(connectionPool);
        categoryDAO = new CategoryDAOImpl(connectionPool);
    }

    @Override
    public List<Product> getList() throws SQLException, ParseException {
        List<Product> products = new ArrayList<>();
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        ResultSet rs = connection.prepareStatement(QUERY.PRODUCT.GET_LIST).executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String size = rs.getString("size");
            String description = rs.getString("description");
            int trademarkId = rs.getInt("trademark_id");
            String categorySku = rs.getString("category_sku");
            Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
            Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
            boolean active = rs.getBoolean("active");
            Trademark trademark = trademarkDAO.get(trademarkId);
            Category category = categoryDAO.get(categorySku);
            Product product = new Product(id, name, size, description, trademark, category, dateCreated, lastUpdated, active);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product get(int id) throws SQLException, ParseException {
        Product product = null;
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.GET_PRODUCT_BY_ID);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst() && rs.getRow() == 0) {
            return null;
        }
        if (rs.next()) {
            String name = rs.getString("name");
            String size = rs.getString("size");
            String description = rs.getString("description");
            int trademarkId = rs.getInt("trademark_id");
            String categorySku = rs.getString("category_sku");
            Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
            Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
            boolean active = rs.getBoolean("active");
            Trademark trademark = trademarkDAO.get(trademarkId);
            Category category = categoryDAO.get(categorySku);
            product = new Product(id, name, size, description, trademark, category, dateCreated, lastUpdated, active);
        }
        return product;
    }

    @Override
    public void create(Product item) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.CREATE);
        statement.setString(1, item.getName());
        statement.setString(2, item.getDescription());
        statement.setInt(3, item.getTrademark().getId());
        statement.setString(4, item.getCategory().getSku());
        statement.executeUpdate();
    }
    
    @Override
    public void update(Product item) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.UPDATE);
        statement.setString(1, item.getName());
        statement.setString(2, item.getDescription());
        statement.setInt(3, item.getTrademark().getId());
        statement.setString(4, item.getCategory().getSku());
        statement.setBoolean(5, item.isActive());
        statement.setInt(6, item.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.DELETE);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public void changeActive(int id) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.CHANGE_ACTIVE);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

//    public Map<String, String> getProductsDiscount() throws SQLException {
//        Map<String, String> productsDiscount = new HashMap<String, String>();
//        connection = connectionPool.getConnection();
//        ResultSet rs = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_PRODUCT_DISCOUNT).executeQuery();
//
//        while (rs.next()) {
//            productsDiscount.put(rs.getString(1), rs.getString(2));
//        }
//        connectionPool.releaseConnection(connection);
//        return productsDiscount;
//    }

    @Override
    public Map<Integer, Map<String, String>> getListData() throws SQLException {
        // productID { {discount}, {price} }
        Map<Integer, Map<String, String>> dataProducts = new HashMap<>();
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.GET_LIST);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Map<String, String> details = new HashMap<>();
            PreparedStatement stm = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_PRODUCT_DETAILS);
            stm.setInt(1, id);
            ResultSet dataProductSet = stm.executeQuery();

            while (dataProductSet.next()) {
                int discount = dataProductSet.getInt("discount");
                int price = dataProductSet.getInt("unit_price");
                String img = dataProductSet.getString("image");
                details.put("discount", "" + discount);
                details.put("price", "" + price);
                details.put("image", img);
                dataProducts.put(id, details);
            }

        }
        connectionPool.releaseConnection(connection);
        return dataProducts;
    }

    @Override
    public Map<Integer, Map<String, String>> getListData(int countProduct) throws SQLException {
        // productID { {discount}, {price}, {img} }

        if (countProduct < 1)
            return getListData();

        int count = 0;
        Map<Integer, Map<String, String>> dataProducts = new HashMap<>();
        List<Product> products = new ArrayList<>();
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.PRODUCT.GET_LIST);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            if (count > countProduct)
                break;

            int id = rs.getInt("id");
            Map<String, String> details = new HashMap<String, String>();
            PreparedStatement stm = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_PRODUCT_DETAILS);
            stm.setInt(1, id);
            ResultSet dataProductSet = stm.executeQuery();

            while (dataProductSet.next()) {
                int discount = dataProductSet.getInt("discount");
                int price = dataProductSet.getInt("unit_price");
                String img = dataProductSet.getString("image");
                details.put("discount", "" + discount);
                details.put("price", "" + price);
                details.put("image", img);
                dataProducts.put(id, details);
            }

            count++;
        }
        connectionPool.releaseConnection(connection);
        return dataProducts;
    }

    @Override
    public List<Product> getList(int countProduct) throws SQLException, ParseException {

        if (countProduct < 1)
            return getList();
        int count = 0;
        List<Product> products = new ArrayList<>();
        connection = connectionPool.getConnection();
        ResultSet rs = connection.prepareStatement(QUERY.PRODUCT.GET_LIST).executeQuery();
        while (rs.next()) {
            if (count >= countProduct)
                break;

            int id = rs.getInt("id");
            String name = rs.getString("name");
            String size = rs.getString("size");
            String description = rs.getString("description");
            int trademarkId = rs.getInt("trademark_id");
            String categorySku = rs.getString("category_sku");
            Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
            Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
            boolean active = rs.getBoolean("active");
            Trademark trademark = trademarkDAO.get(trademarkId);
            Category category = categoryDAO.get(categorySku);
            Product product = new Product(id, name, size, description, trademark, category, dateCreated, lastUpdated, active);
            products.add(product);
            count++;
        }
        connectionPool.releaseConnection(connection);
        return products;
    }

    @Override
    public Map<Integer, Map<String, String>> getCartOrWishlist(String userId, boolean getCart) throws SQLException {
        Map<Integer, Map<String, String>> dataList = new HashMap<>();
        Map<String, String> dataProduct = new HashMap<>();

        connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        if (getCart)
            statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_CART_FROM_USER);
        else
            statement = connection.prepareStatement(QUERY.PRODUCT_DETAIL.GET_WISHLIST_FROM_USER);

        statement.setString(1, userId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            String img = rs.getString("image");
            int price = rs.getInt("unit_price");

            dataProduct.put("name", name);
            dataProduct.put("img", img);
            dataProduct.put("price", "" + price);

            dataList.put(id, dataProduct);
        }

        connectionPool.releaseConnection(connection);
        return dataList;
    }

    @Override
    // if getNewProduct = false => get product which has discount
    public Map<Integer, Map<String, String>> getListByCondition(boolean getNewProduct, int countProduct) throws SQLException {
        Map<Integer, Map<String, String>> dataList = new HashMap<>();
        Map<String, String> dataProduct = new HashMap<>();

        connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        if (getNewProduct)
            statement = connection.prepareStatement(QUERY.PRODUCT.GET_LIST_NEW_BY_COUNT);
        else
            statement = connection.prepareStatement(QUERY.PRODUCT.GET_LIST_HAS_DISCOUNT);

        statement.setInt(1, countProduct);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            String img = rs.getString("image");
            int price = rs.getInt("unit_price");

            dataProduct.put("name", name);
            dataProduct.put("img", img);
            dataProduct.put("price", "" + price);

            dataList.put(id, dataProduct);
        }

        connectionPool.releaseConnection(connection);
        return dataList;
    }
}
