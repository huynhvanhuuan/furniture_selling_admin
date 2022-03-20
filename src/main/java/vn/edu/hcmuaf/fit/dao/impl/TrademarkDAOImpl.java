package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.AddressDAO;
import vn.edu.hcmuaf.fit.dao.TrademarkDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.model.Address;
import vn.edu.hcmuaf.fit.model.Trademark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrademarkDAOImpl implements TrademarkDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;
    private final AddressDAO addressDAO;

    public TrademarkDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        addressDAO = new AddressDAOImpl(connectionPool);
    }

    @Override
    public List<Trademark> getList() throws SQLException {
        List<Trademark> trademarks = new ArrayList<>();
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.GET_LIST);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String website = rs.getString("website");
            List<Address> addresses = addressDAO.getListByTrademarkId(id);
            Trademark trademark = new Trademark(id, name, website, addresses);
            trademarks.add(trademark);
        }
        return trademarks;
    }
    
    @Override
    public List<String> getListNameHasProduct() throws SQLException {
        List<String> names = new ArrayList<>();
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.GET_LIST_NAME_HAS_PRODUCT);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Trademark trademark = get(rs.getInt("trademark_id"));
            names.add(trademark.getName());
        }
        return names;
    }

    @Override
    public Trademark get(int id) throws SQLException {
        Trademark trademark = null;
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.GET_TRADEMARK_BY_ID);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
        if (rs.next()) {
            String name = rs.getString("name");
            String website = rs.getString("website");
            List<Address> addresses = addressDAO.getListByTrademarkId(id);
            trademark = new Trademark(id, name, website, addresses);
        }
        return trademark;
    }

    @Override
    public void create(Trademark trademark) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.CREATE);
        statement.setString(1, trademark.getName());
        statement.setString(2, trademark.getWebsite());
        statement.executeUpdate();
    }
    
    @Override
    public void createAddress(int id, Address address) throws SQLException {
        addressDAO.createTrademarkAddress(id, address);
    }
    
    @Override
    public void update(Trademark trademark) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.UPDATE);
        statement.setString(1, trademark.getName());
        statement.setString(2, trademark.getWebsite());
        statement.setInt(3, trademark.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.DELETE);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    
    @Override
    public void changeActive(int id) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.CHANGE_ACTIVE);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    
    @Override
    public boolean isExist(String name, String website) throws SQLException {
        connection = connectionPool.getConnection();
        connectionPool.releaseConnection(connection);
        PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.FIND_BY_NAME_OR_WEBSITE);
        statement.setString(1, name);
        statement.setString(2, website);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }
}
