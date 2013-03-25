package edu.ycp.cs320.pizza.server;

import java.sql.SQLException;
import java.util.List;

import edu.ycp.cs320.pizza.shared.Order;
import edu.ycp.cs320.pizza.shared.OrderReceipt;

public interface IDatabase {
	public OrderReceipt placeOrder(Order order) throws SQLException;
	
	public List<OrderReceipt> getOrderReceipts() throws SQLException;
}
