package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.AddressDAO;
import vn.edu.hcmuaf.fit.dao.TrademarkDAO;
import vn.edu.hcmuaf.fit.entity.Address;
import vn.edu.hcmuaf.fit.entity.Trademark;

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
    public List<Trademark> findAll() {
        List<Trademark> trademarks = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String website = rs.getString("website");
                List<Address> addresses = addressDAO.findAll();

                Trademark trademark = new Trademark(id, name, website, addresses);
                trademarks.add(trademark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return trademarks;
        }
        connectionPool.releaseConnection(connection);
        return trademarks;
    }

    @Override
    public Trademark findById(Long id) {
        Trademark trademark = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String name = rs.getString("name");
                String website = rs.getString("website");

                List<Address> addresses = addressDAO.findByTrademarkId(id);
                trademark = new Trademark(id, name, website, addresses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return trademark;
    }

    @Override
    public void save(Trademark trademark) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(trademark.getId() == 0 ? QUERY.TRADEMARK.CREATE : QUERY.TRADEMARK.UPDATE);
            statement.setString(1, trademark.getName());
            statement.setString(2, trademark.getWebsite());
            if (trademark.getId() != 0) {
                statement.setLong(3, trademark.getId());
            }
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
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void addAddress(Long trademarkId, Long addressId) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.ADD_ADDRESS);
            statement.setLong(1, trademarkId);
            statement.setLong(2, addressId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trademark findByName(String name) {
        Trademark trademark = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.FIND_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                long id = rs.getLong("id");
                String website = rs.getString("website");

                List<Address> addresses = addressDAO.findByTrademarkId(id);
                trademark = new Trademark(id, name, website, addresses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return trademark;
    }

    @Override
    public Trademark findByWebsite(String website) {
        Trademark trademark = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.TRADEMARK.FIND_BY_WEBSITE);
            statement.setString(1, website);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                List<Address> addresses = addressDAO.findByTrademarkId(id);

                trademark = new Trademark(id, name, website, addresses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return trademark;
    }
}
