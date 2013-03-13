package edu.ycp.cs320.pizza.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ycp.cs320.pizza.client.OrderService;
import edu.ycp.cs320.pizza.shared.Order;

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
		
		// TODO: use a controller to carry out all actions for ordering a pizza
		
		return true;
	}
}
