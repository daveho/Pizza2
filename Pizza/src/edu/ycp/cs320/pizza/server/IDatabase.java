package edu.ycp.cs320.pizza.server;

import edu.ycp.cs320.pizza.shared.Order;

public interface IDatabase {
	public Boolean placeOrder(Order order);
}
