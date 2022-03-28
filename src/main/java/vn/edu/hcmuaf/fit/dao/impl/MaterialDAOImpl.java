package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.MaterialDAO;
import vn.edu.hcmuaf.fit.entity.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    public MaterialDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Material> findAll() {
        List<Material> materials = new ArrayList<>();
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.MATERIAL.FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Material material = new Material(id, name);
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return materials;
        }
        connectionPool.releaseConnection(connection);
        return materials;
    }

    @Override
    public Material findById(Long id) {
        Material material = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.MATERIAL.FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                material = new Material(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return material;
    }

    @Override
    public void save(Material object) {

    }

    @Override
    public void delete(Long id) {

    }
}
