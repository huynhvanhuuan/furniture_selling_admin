package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.*;
import vn.edu.hcmuaf.fit.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private final TrademarkDAO trademarkDAO;
    private final UserDAO userDAO;
    private final DistrictDAO districtDAO;
    private final WardDAO wardDAO;

    public AddressDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.trademarkDAO = new TrademarkDAOImpl(connectionPool);
        this.userDAO = new UserDAOImpl(connectionPool);
        this.districtDAO = new DistrictDAOImpl(connectionPool);
        this.wardDAO = new WardDAOImpl(connectionPool);
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String number = rs.getString("number");
                String street = rs.getString("street");
                Ward ward = wardDAO.findById(rs.getLong("ward_id"));
                District district = districtDAO.findById(rs.getLong("district_id"));
                String path = rs.getString("path");

                Address address = new Address(id, number, street, ward, district, path);
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return addresses;
        }
        connectionPool.releaseConnection(connection);
        return addresses;
    }

    @Override
    public Address findById(Long id) {
        Address address = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String number = rs.getString("number");
                String street = rs.getString("street");
                Ward ward = wardDAO.findById(rs.getLong("ward_id"));
                District district = districtDAO.findById(rs.getLong("district_id"));
                String path = rs.getString("path");

                address = new Address(id, number, street, ward, district, path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return address;
    }

    @Override
    public void save(Address address) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.CREATE);
            statement.setString(1, address.getNumber());
            statement.setString(2, address.getStreet());
            statement.setLong(3, address.getWard() == null ? 0 : address.getWard().getId());
            statement.setLong(4, address.getDistrict().getId());
            statement.setString(5, address.getPath());
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
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public List<Address> findByTrademarkId(Long trademarkId) {
        Trademark trademark = trademarkDAO.findById(trademarkId);
        List<Address> addresses = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            connectionPool.releaseConnection(connection);
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.FIND_BY_TRADEMARK_ID);
            statement.setLong(1, trademarkId);
            ResultSet rs = statement.executeQuery();
            addToList(rs, addresses);
        } catch (SQLException e) {
            e.printStackTrace();
            return addresses;
        }
        return addresses;
    }
    
    @Override
    public List<Address> findByUserId(int userId) {
        User user = userDAO.findById(userId);
        List<Address> addresses = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.ADDRESS.FIND_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String street = rs.getString("street");
                int wardId = rs.getInt("ward_id");
                int districtId = rs.getInt("district_id");
                String path = rs.getString("path");
                Address address;
                if (wardId == 0) address = new Address(id, number, street, null, getDistrict(districtId), path);
                else {
                    Ward ward = getWard(wardId);
                    address = new Address(id, number, street, ward, ward.getDistrict(), path);
                }
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return addresses;
        }
        connectionPool.releaseConnection(connection);
        return addresses;
    }

    @Override
    public Address findByPath(String path) {
        return null;
    }

    private void addToList(ResultSet rs, List<Address> addresses) throws SQLException {
        while (rs.next()) {
            long id = rs.getLong("id");
            String number = rs.getString("number");
            String street = rs.getString("street");
            Ward ward = wardDAO.findById(rs.getLong("ward_id"));
            District district = districtDAO.findById(rs.getLong("district_id"));
            String path = rs.getString("path");
            Address address = new Address(id, number, street, ward, district, path);
            addresses.add(address);
        }
    }

}
