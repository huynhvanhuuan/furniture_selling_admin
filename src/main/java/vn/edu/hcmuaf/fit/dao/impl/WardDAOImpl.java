package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.DistrictDAO;
import vn.edu.hcmuaf.fit.dao.WardDAO;
import vn.edu.hcmuaf.fit.entity.District;
import vn.edu.hcmuaf.fit.entity.Ward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardDAOImpl implements WardDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private DistrictDAO districtDAO;

    public WardDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.districtDAO = new DistrictDAOImpl(connectionPool);
    }

    @Override
    public List<Ward> findAll() {
        List<Ward> wards = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.WARD.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");
                District district = districtDAO.findById(rs.getLong("district_id"));
                Ward ward = new Ward(id, name, prefix, district);
                wards.add(ward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return wards;
        }
        connectionPool.releaseConnection(connection);
        return wards;
    }

    @Override
    public Ward findById(Long id) {
        Ward ward = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.WARD.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;

            if (rs.next()) {
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");
                District district = districtDAO.findById(rs.getLong("district_id"));

                ward = new Ward(id, name, prefix, district);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return ward;
    }

    @Override
    public void save(Ward object) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Ward> findByDistrictId(Long districtId) {
        District district = districtDAO.findById(districtId);
        List<Ward> wards = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.WARD.FIND_BY_DISTRICT_ID);
            statement.setLong(1, districtId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");

                Ward ward = new Ward(id, name, prefix, district);
                wards.add(ward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return wards;
        }
        connectionPool.releaseConnection(connection);
        return wards;
    }
}
