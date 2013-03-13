package edu.ycp.cs320.pizza.client;

import java.math.BigDecimal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.ycp.cs320.pizza.shared.Order;
import edu.ycp.cs320.pizza.shared.Pizza;
import edu.ycp.cs320.pizza.shared.Size;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PizzaApp implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		FlowPanel panel = new FlowPanel();
		
//		PizzaView pizzaView = new PizzaView();
//		
//		panel.add(pizzaView);
//		Pizza model = new Pizza();
//		model.setSize(Size.LARGE);
//		pizzaView.setModel(model);
//		pizzaView.update();
		
		OrderView orderView = new OrderView();
		
		Order order = new Order();
		order.setPizza(new Pizza());
		order.setUser("default user");
		order.setPrice(new BigDecimal(0));
		
		orderView.setModel(order);
		
		panel.add(orderView);

		RootLayoutPanel.get().add(panel);
		RootLayoutPanel.get().setWidgetTopBottom(panel, 10.0, Unit.PX, 10.0, Unit.PX);
		RootLayoutPanel.get().setWidgetLeftRight(panel, 10.0, Unit.PX, 10.0, Unit.PX);
	}
}
