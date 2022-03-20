package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.dao.impl.UserDAOImpl;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl(DbManager.connectionPool);
    }

    @Override
    public boolean register(String firstName, String lastName, String phone, String gender, String email, String password, String confirm) throws SQLException {
        // check field
        if (!password.equals(confirm))
            return false;
        if (checkUser(email))
            return false;

        userDAO.create(new User(firstName, lastName, phone, gender, email, password));
        return true;
    }

    @Override
    public User login(String email, String password) throws SQLException {
        return userDAO.checkLogin(email, password);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("auth");
    }

    @Override
    public void update(User item) throws SQLException {
        userDAO.update(item);
    }

    @Override
    public void delete(String id) throws SQLException {
        userDAO.delete(id);
    }

    private boolean checkUser(String email) throws SQLException {
        return userDAO.checkUser(email);
    }
}
