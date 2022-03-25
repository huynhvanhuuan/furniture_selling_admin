package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.ProvinceDAO;
import vn.edu.hcmuaf.fit.entity.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAOImpl implements ProvinceDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    public ProvinceDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Province> findAll() {
        List<Province> provinces = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.PROVINCE.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String prefix = rs.getString("prefix");
                String name = rs.getString("name");
                Province province = new Province(id, prefix, name);
                provinces.add(province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return provinces;
        }
        connectionPool.releaseConnection(connection);
        return provinces;
    }

    @Override
    public Province findById(Long id) {
        Province province = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.PROVINCE.FIND_PROVINCE_BY_ID);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String prefix = rs.getString("prefix");
                String name = rs.getString("name");
                province = new Province(id, prefix, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return province;
    }

    @Override
    public void save(Province object) {
    }

    @Override
    public void delete(Long id) {
    }
}
