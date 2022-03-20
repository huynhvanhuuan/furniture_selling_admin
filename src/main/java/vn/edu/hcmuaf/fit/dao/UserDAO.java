package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
	List<User> getList() throws SQLException;
	User get(String id) throws SQLException;
	void create(User item) throws SQLException;
	void update(User item) throws SQLException;
	void delete(String id) throws SQLException;
	boolean checkUser(String email) throws SQLException;
	User checkLogin(String email, String password) throws SQLException;
}
