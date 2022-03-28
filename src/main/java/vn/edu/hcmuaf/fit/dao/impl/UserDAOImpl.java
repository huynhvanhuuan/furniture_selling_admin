package vn.edu.hcmuaf.fit.dao.impl;

import vn.edu.hcmuaf.fit.config.IConnectionPool;
import vn.edu.hcmuaf.fit.constant.QUERY;
import vn.edu.hcmuaf.fit.dao.*;
import vn.edu.hcmuaf.fit.entity.Role;
import vn.edu.hcmuaf.fit.entity.User;
import vn.edu.hcmuaf.fit.entity.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserDAOImpl implements UserDAO {
    private final IConnectionPool connectionPool;
    private Connection connection;

    private final RoleDAO roleDAO;
    private final WishlistDAO wishlistDAO;
    private final AddressDAO addressDAO;
    private final OrderDAO orderDAO;

    public UserDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.roleDAO = new RoleDAOImpl(connectionPool);
        this.wishlistDAO = new WishlistDAOImpl(connectionPool);
        this.addressDAO = new AddressDAOImpl(connectionPool);
        this.orderDAO = new OrderDAOImpl(connectionPool);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            ResultSet rs = connection.prepareStatement(QUERY.USER.FIND_ALL).executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Date dateOfBirth = rs.getDate("date_of_birth");
                boolean isFemale = rs.getBoolean("is_female");
                String profileImageUrl = rs.getString("profile_image_url");
                Date dateCreated = rs.getDate("date_created");
                Date lastUpdated = rs.getDate("last_updated");
                Role role = roleDAO.findById(rs.getLong("role_id"));
                boolean isNotLocked = rs.getBoolean("is_not_locked");
                boolean active = rs.getBoolean("active");
                Set<Wishlist> wishlists = wishlistDAO.findByUserId(id);
                users.add(new User());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return users;
        }
        connectionPool.releaseConnection(connection);
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        connection = connectionPool.getConnection();
        //PreparedStatement statement = connection.prepareStatement(QUERY.USER.GET_ITEM_BY_ID);
        connectionPool.releaseConnection(connection);
        return user;
    }

    @Override
    public void save(User user) {
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
    public void delete(Long id) {
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.USER.DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY.USER.FIND_BY_EMAIL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String fullName = rs.getString("first_name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                Date dateOfBirth = rs.getDate("date_of_birth");
                boolean isFemale = rs.getBoolean("is_female");
                String profileImageUrl = rs.getString("profile_image_url");
                Date dateCreated = rs.getDate("date_created");
                Date lastUpdated = rs.getDate("last_updated");
                boolean isVerified = rs.getBoolean("is_verified");
                Role role = roleDAO.findById(rs.getLong("role_id"));
                boolean isNotLock = rs.getBoolean("is_not_lock");
                boolean active = rs.getBoolean("active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        connectionPool.releaseConnection(connection);
        return user;
    }

}
