package edu.ycp.cs320.pizza.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.ListBox;

import edu.ycp.cs320.pizza.shared.IPublisher;
import edu.ycp.cs320.pizza.shared.ISubscriber;
import edu.ycp.cs320.pizza.shared.Pizza;
import edu.ycp.cs320.pizza.shared.Size;
import edu.ycp.cs320.pizza.shared.Topping;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class PizzaView extends Composite implements ISubscriber {
	private Pizza model;
	
	private InlineLabel sizeLabel;
	private InlineLabel toppingsLabel;
	private ListBox sizeComboBox;
	private ListBox selectedToppingsList;
	private ListBox availToppingsList;
	
	public PizzaView() {
		
		LayoutPanel layoutPanel = new LayoutPanel();
		initWidget(layoutPanel);
		
		sizeLabel = new InlineLabel("Size:");
		layoutPanel.add(sizeLabel);
		layoutPanel.setWidgetLeftWidth(sizeLabel, 17.0, Unit.PX, 90.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(sizeLabel, 43.0, Unit.PX, 18.0, Unit.PX);
		
		toppingsLabel = new InlineLabel("Toppings:");
		layoutPanel.add(toppingsLabel);
		layoutPanel.setWidgetLeftWidth(toppingsLabel, 17.0, Unit.PX, 90.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(toppingsLabel, 93.0, Unit.PX, 18.0, Unit.PX);
		
		sizeComboBox = new ListBox();
		sizeComboBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				handleChangeSize();
			}
		});
		layoutPanel.add(sizeComboBox);
		layoutPanel.setWidgetLeftWidth(sizeComboBox, 82.0, Unit.PX, 191.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(sizeComboBox, 43.0, Unit.PX, 26.0, Unit.PX);
		
		selectedToppingsList = new ListBox();
		layoutPanel.add(selectedToppingsList);
		layoutPanel.setWidgetLeftWidth(selectedToppingsList, 82.0, Unit.PX, 120.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(selectedToppingsList, 93.0, Unit.PX, 189.0, Unit.PX);
		selectedToppingsList.setVisibleItemCount(5);
		
		availToppingsList = new ListBox();
		layoutPanel.add(availToppingsList);
		layoutPanel.setWidgetLeftWidth(availToppingsList, 323.0, Unit.PX, 127.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(availToppingsList, 93.0, Unit.PX, 189.0, Unit.PX);
		availToppingsList.setVisibleItemCount(5);
		
		Button addToppingsButton = new Button("New button");
		addToppingsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleAddTopping();
			}
		});
		addToppingsButton.setText("<< Add");
		layoutPanel.add(addToppingsButton);
		layoutPanel.setWidgetLeftWidth(addToppingsButton, 208.0, Unit.PX, 104.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(addToppingsButton, 131.0, Unit.PX, 30.0, Unit.PX);
		
		Button removeToppingsButton = new Button("New button");
		removeToppingsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleRemoveTopping();
			}
		});
		removeToppingsButton.setText("Remove >>");
		layoutPanel.add(removeToppingsButton);
		layoutPanel.setWidgetLeftWidth(removeToppingsButton, 208.0, Unit.PX, 98.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(removeToppingsButton, 182.0, Unit.PX, 30.0, Unit.PX);
	}

	protected void handleChangeSize() {
		int selIndex = sizeComboBox.getSelectedIndex();
		if (selIndex >= 0) {
			Size size = Size.valueOf(sizeComboBox.getItemText(selIndex));
			model.setSize(size);
		}
	}

	protected void handleAddTopping() {
		int index = availToppingsList.getSelectedIndex();
		if (index >= 0) {
			String item = availToppingsList.getItemText(index);
			Topping t = Topping.valueOf(item);
			model.addTopping(t);
		}
	}
	
	protected void handleRemoveTopping() {
		int index = selectedToppingsList.getSelectedIndex();
		if (index >= 0) {
			String item = selectedToppingsList.getItemText(index);
			Topping t = Topping.valueOf(item);
			model.removeTopping(t);
		}
	}

	public void setModel(Pizza model) {
		this.model = model;
		this.model.subscribe(Pizza.Events.SIZE_CHANGED, this);
		this.model.subscribe(Pizza.Events.ADD_TOPPING, this);
		this.model.subscribe(Pizza.Events.REMOVE_TOPPING, this);
	}
	
	public void update() {
		// If the size combo box hasn't been updated yet,
		// add all of the possible size values
		if (sizeComboBox.getItemCount() == 0) {
			Size[] sizes = Size.values();
			for (Size s : sizes) {
				sizeComboBox.addItem(s.toString());
			}
		}
		
		// Select the current pizza size in the combo box
		Size pizzaSize = model.getSize();
		sizeComboBox.setSelectedIndex(pizzaSize.ordinal());
		
		// Update toppings list boxes
		selectedToppingsList.clear();
		availToppingsList.clear();
		for (Topping t : Topping.values()) {
			if (model.getToppingList().contains(t)) {
				selectedToppingsList.addItem(t.toString());
			} else {
				availToppingsList.addItem(t.toString());
			}
		}
	}
	
	@Override
	public void eventOccurred(Object key, IPublisher publisher, Object hint) {
		update();
	}
}
