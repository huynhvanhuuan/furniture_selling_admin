package vn.edu.hcmuaf.fit.dao.impl;

import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.DistrictDAO;
import vn.edu.hcmuaf.fit.dao.ProvinceDAO;
import vn.edu.hcmuaf.fit.entity.District;
import vn.edu.hcmuaf.fit.entity.Product;
import vn.edu.hcmuaf.fit.entity.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDAOImpl implements DistrictDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private ProvinceDAO provinceDAO;

    public DistrictDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.provinceDAO = new ProvinceDAOImpl(connectionPool);
    }

    @Override
    public List<District> findAll() {
        List<District> districts = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.DISTRICT.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");
                Province province = provinceDAO.findById(rs.getLong("id"));
                District district = new District(id, name, prefix, province);
                districts.add(district);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return districts;
        }
        return districts;
    }

    @Override
    public District findById(Long id) {
        District district = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.DISTRICT.FIND_DISTRICT_BY_ID);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() && rs.getRow() == 0) return null;
            if (rs.next()) {
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");
                Province province = provinceDAO.findById(rs.getLong("province_id"));
                district = new District(id, name, prefix, province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return district;
    }

    @Override
    public List<District> findByProvinceId(long provinceId) {
        Province province = provinceDAO.findById(provinceId);
        List<District> districts = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.DISTRICT.FIND_DISTRICT_BY_PROVINCE_ID);
            statement.setLong(1, provinceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String prefix = rs.getString("prefix");
                District district = new District(id, name, prefix, province);
                districts.add(district);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return districts;
        }
        return districts;
    }

    @Override
    public void save(District object) {

    }

    @Override
    public void delete(Long id) {

    }
}
