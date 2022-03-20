package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.WishlistDAO;
import vn.edu.hcmuaf.fit.dao.impl.WishlistDAOImpl;
import vn.edu.hcmuaf.fit.dto.wishlist.Wishlist;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.WishlistService;

import java.sql.SQLException;
import java.text.ParseException;

public class WishlistServiceImpl implements WishlistService {
    private final WishlistDAO wishlistDAO;

    public WishlistServiceImpl() {
        this.wishlistDAO = new WishlistDAOImpl(DbManager.connectionPool);
    }

    @Override
    public Wishlist getList(User user) throws SQLException, ParseException {
        return wishlistDAO.getList(user);
    }

    @Override
    public void add(String userId, String productSku) throws SQLException {
        wishlistDAO.add(userId, productSku);
    }

    @Override
    public void remove(String userId, String productSku) throws SQLException {
        wishlistDAO.remove(userId, productSku);
    }

    @Override
    public void removeAll(String userId) throws SQLException {
        wishlistDAO.removeAll(userId);
    }
}
