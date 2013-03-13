package edu.ycp.cs320.pizza.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.ycp.cs320.pizza.shared.Order;

@RemoteServiceRelativePath("order")
public interface OrderService extends RemoteService {
	/**
	 * Just for testing.
	 * 
	 * @param message a message to send to the server.
	 * @return true if successful, false otherwise
	 */
	public Boolean hello(String message);
	
	/**
	 * Place an order.
	 * 
	 * @param the Order to place
	 * @return true if successful, false otherwise
	 */
	public Boolean placeOrder(Order order);
}
