package vn.edu.hcmuaf.fit.entity;

import java.io.Serializable;

public class Color implements Serializable {
	private int id;
	private String name;
	private String hex;
	
	public Color() {
	}
	
	public Color(int id, String name, String hex) {
		this.id = id;
		this.name = name;
		this.hex = hex;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHex() {
		return hex;
	}
	
	public void setHex(String hex) {
		this.hex = hex;
	}
}
