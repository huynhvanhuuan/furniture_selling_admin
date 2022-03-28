package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class OrderItem implements Serializable {
	private Warehouse product;
	private int quantity;
	private Order order;
	
	public OrderItem() {
	}
	
	public OrderItem(Warehouse product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Warehouse getProduct() {
		return product;
	}
	
	public void setProduct(Warehouse product) {
		this.product = product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
}
