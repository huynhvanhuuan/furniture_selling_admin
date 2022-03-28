package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class Wishlist implements Serializable {
	private User user;
	private Warehouse productList;
	
	public Wishlist() {
	}
	
	public Wishlist(User user, Warehouse productList) {
		this.user = user;
		this.productList = productList;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Warehouse getListProduct() {
		return productList;
	}
	
	public void setListProduct(Warehouse productList) {
		this.productList = productList;
	}

}
