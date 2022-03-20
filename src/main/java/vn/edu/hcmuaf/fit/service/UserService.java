package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public interface UserService {
    boolean register(String firstName, String lastName, String phone, String gender, String email, String password, String confirm) throws SQLException;
    User login(String email, String password) throws SQLException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    void update(User item) throws SQLException;
    void delete(String id) throws SQLException;
}
