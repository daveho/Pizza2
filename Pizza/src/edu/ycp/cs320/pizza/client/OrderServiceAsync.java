package edu.ycp.cs320.pizza.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ycp.cs320.pizza.shared.Order;

public interface OrderServiceAsync {

	void hello(String message, AsyncCallback<Boolean> callback);

	void placeOrder(Order order, AsyncCallback<Boolean> callback);

}
