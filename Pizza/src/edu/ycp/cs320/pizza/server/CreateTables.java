package edu.ycp.cs320.pizza.server;

import java.sql.SQLException;

public class CreateTables {

	public static void main(String[] args) throws SQLException {
		System.out.println("Initializing database...");
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("Creating tables...");
		db.createTables();
		System.out.println("Successfully created tables");
	}

}
