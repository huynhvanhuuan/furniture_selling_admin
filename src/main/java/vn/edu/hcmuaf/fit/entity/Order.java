package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
	private String id;
	private String uesrId;
	private long totalPrice;
	private String address;
	private Date dateCreated;
	private Date lastUpdated;
	private List<OrderItem> orderItems;
	
	public Order() {
	}
	
	public Order(String id, String uesrId, long totalPrice, String address, Date dateCreated, Date lastUpdated, List<OrderItem> orderItems) {
		this.id = id;
		this.uesrId = uesrId;
		this.totalPrice = totalPrice;
		this.address = address;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.orderItems = orderItems;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUesrId() {
		return uesrId;
	}
	
	public void setUesrId(String uesrId) {
		this.uesrId = uesrId;
	}
	
	public long getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void addItem(OrderItem item) {
		if (orderItems == null) orderItems = new ArrayList<>();
		orderItems.add(item);
		item.setOrder(this);
	}
}
