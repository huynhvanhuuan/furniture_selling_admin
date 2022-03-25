package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.DistrictDAO;
import vn.edu.hcmuaf.fit.dao.ProvinceDAO;
import vn.edu.hcmuaf.fit.entity.District;
import vn.edu.hcmuaf.fit.entity.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProvinceDAOImpl implements ProvinceDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private final DistrictDAO districtDAO;

    public ProvinceDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.districtDAO = new DistrictDAOImpl(connectionPool);
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
                Set<District> districts = new HashSet<>(districtDAO.findByProvinceId(id));

                Province province = new Province(id, prefix, name, districts);
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
            PreparedStatement statement = connection.prepareStatement(QUERY.PROVINCE.FIND_BY_ID);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String prefix = rs.getString("prefix");
                String name = rs.getString("name");
                Set<District> districts = new HashSet<>(districtDAO.findByProvinceId(id));

                province = new Province(id, prefix, name, districts);
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
