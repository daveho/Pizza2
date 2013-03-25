package edu.ycp.cs320.pizza.server;

import java.sql.SQLException;
import java.util.List;

import edu.ycp.cs320.pizza.shared.OrderReceipt;

public class ViewOrderReceipts {
	public static void main(String[] args) throws SQLException {
		DerbyDatabase db = new DerbyDatabase();
		
		List<OrderReceipt> receipts = db.getOrderReceipts();
		for (OrderReceipt receipt : receipts) {
			System.out.println(receipt.getId() + "," + receipt.getUserInfo() + "," + receipt.getPrice());
		}
	}
}
