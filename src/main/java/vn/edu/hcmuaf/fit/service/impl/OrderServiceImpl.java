package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.OrderDAO;
import vn.edu.hcmuaf.fit.dao.impl.OrderDAOImpl;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.Order;
import vn.edu.hcmuaf.fit.service.OrderService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	private OrderDAO orderDAO;
	
	public OrderServiceImpl() {
		orderDAO = new OrderDAOImpl(DbManager.connectionPool);
	}
	
	@Override
	public List<Order> getListByUserId(String userId) throws SQLException, ParseException {
		return orderDAO.getListByUserId(userId);
	}
	
	@Override
	public void create(Order order) throws SQLException, ParseException {
		orderDAO.create(order);
	}
	
	@Override
	public void update(Order order) throws SQLException, ParseException {
		orderDAO.update(order);
	}
}
