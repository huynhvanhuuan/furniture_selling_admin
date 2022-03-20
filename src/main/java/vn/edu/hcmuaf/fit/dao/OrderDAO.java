package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.model.Order;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface OrderDAO {
    List<Order> getListByUserId(String userId) throws SQLException, ParseException;
    void create(Order order) throws SQLException, ParseException;
    void update(Order order) throws SQLException, ParseException;
}
