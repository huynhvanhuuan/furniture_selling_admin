package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wishlist implements Serializable {
	private User user;
	private List<Warehouse> productList;
	
	public Wishlist() {
	}
	
	public Wishlist(User user, List<Warehouse> productList) {
		this.user = user;
		this.productList = productList;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Warehouse> getListProduct() {
		return productList;
	}
	
	public void setListProduct(List<Warehouse> productList) {
		this.productList = productList;
	}

	public void addProduct(Warehouse product) {
		if (productList == null) productList = new ArrayList<>();
		productList.add(product);
	}
}
