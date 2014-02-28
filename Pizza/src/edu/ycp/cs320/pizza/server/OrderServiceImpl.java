package edu.ycp.cs320.pizza.server;

import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ycp.cs320.pizza.client.OrderService;
import edu.ycp.cs320.pizza.shared.Order;
import edu.ycp.cs320.pizza.shared.OrderReceipt;

public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean hello(String message) {
		System.out.println("Hello: " + message);
		return true;
	}

	@Override
	public Boolean placeOrder(Order order) {
		System.out.println("Ordering a " + order.getPizza().getSize() + " pizza");
		System.out.println("Price is $" + order.getPrice().toString());
		
		try {
			OrderReceipt receipt = DBUtil.instance().placeOrder(order);
			System.out.println("Placed order: receipt id=" + receipt.getId());
			return true;
		} catch (SQLException e) {
			System.out.println("Error occurred inserting order receipt: " + e.getMessage());
			return false;
		}
	}
}
