package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.entity.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> findByUserId(Long userId);
}
