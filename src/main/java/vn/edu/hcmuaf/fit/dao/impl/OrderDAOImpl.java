package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.OrderDAO;
import vn.edu.hcmuaf.fit.dao.WarehouseDAO;
import vn.edu.hcmuaf.fit.model.Order;
import vn.edu.hcmuaf.fit.model.OrderItem;
import vn.edu.hcmuaf.fit.model.ProductDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;
    private final WarehouseDAO warehouseDAO;

    public OrderDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        warehouseDAO = new WarehouseDAOImpl(connectionPool);
    }

    @Override
    public List<Order> getListByUserId(String userId) throws SQLException, ParseException {
        List<Order> list = new ArrayList<>();
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.ORDER.FIND_ALL);
        statement.setString(1, userId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String orderId = rs.getString("order_id");
            BigDecimal totalPrice = rs.getBigDecimal("total_price");
            String address = rs.getString("address");
            Date dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_created"));
            Date lastUpdated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_updated"));
            List<OrderItem> listDetail = getListDetail(orderId);
            Order order = new Order(orderId, userId, totalPrice.intValue(), address, dateCreated, lastUpdated, listDetail);
            list.add(order);
        }

        return list;
    }

    @Override
    public void create(Order order) throws SQLException, ParseException {
        
    }
    
    @Override
    public void update(Order order) throws SQLException, ParseException {
        
    }
    
    public List<OrderItem> getListDetail(String orderId) throws SQLException, ParseException {
        List<OrderItem> list = new ArrayList<>();
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.ORDER.GET_DETAIL);
        statement.setString(1, orderId);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String productSku = rs.getString("product_sku");
            int quantity = rs.getInt("quantity");
            ProductDetail product = warehouseDAO.getProduct(productSku);

            list.add(new OrderItem(product, quantity));
        }

        return list;
    }
}
