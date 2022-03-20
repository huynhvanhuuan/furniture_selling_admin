package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.model.Order;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface OrderService {
	List<Order> getListByUserId(String userId) throws SQLException, ParseException;
	void create(Order order) throws SQLException, ParseException;
	void update(Order order) throws SQLException, ParseException;
}
