package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.entity.User;
import vn.edu.hcmuaf.fit.entity.Wishlist;

import java.sql.SQLException;
import java.text.ParseException;

public interface WishlistService {
    Wishlist getList(User user) throws SQLException, ParseException;
    void add(String userId, String productSku) throws SQLException;
    void remove(String userId, String productSku) throws SQLException;
    void removeAll(String userId) throws SQLException;
}
