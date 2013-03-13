package edu.ycp.cs320.pizza.shared;

import java.math.BigDecimal;

public class PizzaPriceController {
	public BigDecimal calcPrice(Pizza pizza) {
		BigDecimal price = new BigDecimal(0);
		
		switch (pizza.getSize()) {
		case SMALL:
			price = price.add(new BigDecimal(5));
			break;
		case MEDIUM:
			price = price.add(new BigDecimal(6));
			break;
		case LARGE:
			price = price.add(new BigDecimal(7));
			break;
		}
		
		for (Topping t : pizza.getToppingList()) {
			price = price.add(new BigDecimal("0.50"));
		}
		
		return price;
	}
}
