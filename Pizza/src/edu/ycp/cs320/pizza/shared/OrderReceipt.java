package edu.ycp.cs320.pizza.shared;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderReceipt implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String userInfo;
	private BigDecimal price;
	
	public OrderReceipt() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
	public String getUserInfo() {
		return userInfo;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
}
