package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.database.IConnectionPool;
import vn.edu.hcmuaf.fit.database.QUERY;
import vn.edu.hcmuaf.fit.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    public UserDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<User> getList() throws SQLException {
        List<User> users = new ArrayList<>();
        connection = connectionPool.getConnection();
        ResultSet rs = connection.prepareStatement(QUERY.USER.GET_LIST).executeQuery();
        while (rs.next()) {
            users.add(new User());
        }
        connectionPool.releaseConnection(connection);
        return users;
    }

    @Override
    public User get(String id) throws SQLException {
        User user = null;
        connection = connectionPool.getConnection();
        //PreparedStatement statement = connection.prepareStatement(QUERY.USER.GET_ITEM_BY_ID);
        connectionPool.releaseConnection(connection);
        return user;
    }

    @Override
    public void create(User item) throws SQLException {
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.USER.CREATE_USER);
        statement.setString(1, item.getPhone() + item.getFirstName());
        statement.setString(2, item.getFirstName());
        statement.setString(3, item.getLastName());
        statement.setString(4, item.getFirstName() + " " + item.getLastName());
        statement.setString(5, hashPassword(item.getPassword()));
        statement.setString(6, item.getEmail());
        statement.setString(7, item.getPhone());
        statement.setBoolean(8, item.getGender().equals("female"));
        statement.setString(9, item.getProfileImageUrl());
        statement.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getDateCreated()));
        statement.setString(11, "customer");
        statement.executeUpdate();
        connectionPool.releaseConnection(connection);
    }
    
    @Override
    public void update(User item) throws SQLException {
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.USER.UPDATE);
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, hashPassword(item.getPassword()));
        statement.setString(4, item.getEmail());
        statement.setString(5, item.getPhone());
        statement.executeUpdate();
    }

    @Override
    public void delete(String id) throws SQLException {
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.USER.DELETE);
        statement.setString(1, id);
        statement.executeUpdate();
    }

    @Override
    public boolean checkUser(String email) throws SQLException {
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.USER.GET_USER_BY_EMAIL);
        statement.setString(1, email);
        ResultSet rs = statement.executeQuery();

        if (rs.next())
            return false;

        connectionPool.releaseConnection(connection);
        return true;
    }

    @Override
    public User checkLogin(String email, String password) throws SQLException {
        List<User> users = new ArrayList<>();
        connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(QUERY.USER.GET_USER_BY_EMAIL);
        statement.setString(1, email);
        ResultSet rs = statement.executeQuery();

        User user = null;
        while (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setPassword(rs.getString("password"));
            user.setGender(rs.getBoolean("female") ? "female" : "male");
            user.setProfileImageUrl(rs.getString("profile_image_url"));
            users.add(user);
        }

        if (users.size() != 1) return null;
        user = users.get(0);
        if (!user.getPassword().equals(hashPassword(password)) || !user.getEmail().equals(email))  return null;

        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest sha256 = null;
            sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(password.getBytes());
            BigInteger number = new BigInteger(1, hash);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
