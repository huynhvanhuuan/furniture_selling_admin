package vn.edu.hcmuaf.fit.service.impl;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.dao.impl.UserDAOImpl;
import vn.edu.hcmuaf.fit.dto.checkout.Checkout;
import vn.edu.hcmuaf.fit.dto.checkout.CheckoutResponse;
import vn.edu.hcmuaf.fit.helper.DbManager;
import vn.edu.hcmuaf.fit.model.Order;
import vn.edu.hcmuaf.fit.model.OrderItem;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.CheckoutService;

import java.util.List;
import java.util.UUID;

public class CheckoutServiceImpl implements CheckoutService {
	private UserDAO userDAO;
	
	public CheckoutServiceImpl() {
		userDAO = new UserDAOImpl(DbManager.connectionPool);
	}
	
	@Override
	public CheckoutResponse createOrder(Checkout checkout) {
		Order order = checkout.getOrder();
		
		// Generate id <==> tracking number
		String orderTrackingNumber = UUID.randomUUID().toString();
		order.setId(orderTrackingNumber);
		
		// Get order items
		List<OrderItem> orderItems = checkout.getOrderItems();
		orderItems.forEach(order::addItem);
		
		//  Get address
		order.setAddress(checkout.getAddress());
		
		// Get user information
		User user = checkout.getUser();
		
		return null;
	}
}
