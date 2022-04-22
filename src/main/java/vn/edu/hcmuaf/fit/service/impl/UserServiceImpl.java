package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.dao.impl.UserDAOImpl;
import vn.edu.hcmuaf.fit.dto.user.UserCreate;
import vn.edu.hcmuaf.fit.dto.user.UserLoginRes;
import vn.edu.hcmuaf.fit.entity.User;
import vn.edu.hcmuaf.fit.infrastructure.DbManager;
import vn.edu.hcmuaf.fit.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public boolean register(UserCreate userCreate) {
        return false;
    }

    @Override
    public UserLoginRes login(String email, String password) {
        return null;
    }

    @Override
    public void updateProfile(User item) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void lockUser(Long id) {

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
