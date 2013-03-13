package edu.ycp.cs320.pizza.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;

import edu.ycp.cs320.pizza.shared.IPublisher;
import edu.ycp.cs320.pizza.shared.ISubscriber;
import edu.ycp.cs320.pizza.shared.Order;

public class OrderView extends Composite implements ISubscriber {
	private Order model;
	private PizzaView pizzaView;
	private TextBox userTextBox;
	private TextBox priceTextBox;
	private Button orderButton;
	
	public OrderView() {
		FlowPanel panel = new FlowPanel();
		
		this.pizzaView = new PizzaView();
		panel.add(pizzaView);
		
		FlowPanel otherStuff = new FlowPanel();
		
		otherStuff.add(new InlineLabel("User: "));
		userTextBox = new TextBox();
		userTextBox.setSize("160px", "28px");
		otherStuff.add(userTextBox);
		
		otherStuff.add(new InlineLabel("Price: "));
		priceTextBox = new TextBox();
		priceTextBox.setSize("100px", "28px");
		priceTextBox.setEnabled(false);
		otherStuff.add(priceTextBox);
		
		orderButton = new Button("Order!");
		orderButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleOrder();
			}
		});
		otherStuff.add(orderButton);
		
		panel.add(otherStuff);
		
		initWidget(panel);
	}
	
	protected void handleOrder() {
		RPC.orderService.placeOrder(model, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				GWT.log("Order succeeded!");
				
				// FIXME: should update the UI to inform the user of the completion of the order
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Order failure", caught);
				
				// FIXME: should update the UI to inform the user of the error
			}
		});
	}

	public void setModel(Order model) {
		this.model = model;
		this.pizzaView.setModel(model.getPizza());
		pizzaView.update();
		
		model.subscribe(Order.Events.PRICE_CHANGED, this);
	}
	
	@Override
	public void eventOccurred(Object key, IPublisher publisher, Object hint) {
		update();
	}
	
	public void update() {
		priceTextBox.setText(model.getPrice().toString());
	}
}
