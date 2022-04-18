package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.entity.Order;

import java.util.List;

public interface OrderService {
	List<Order> getOrderByUserId(Long userId);
}
