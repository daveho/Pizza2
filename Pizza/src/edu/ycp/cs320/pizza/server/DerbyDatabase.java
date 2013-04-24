package edu.ycp.cs320.pizza.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.pizza.shared.Order;
import edu.ycp.cs320.pizza.shared.OrderReceipt;

public class DerbyDatabase implements IDatabase {
	private static final String DATASTORE = "H:/pizzadb";
	
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new RuntimeException("Could not load Derby JDBC driver");
		}
	}
	
	private class DatabaseConnection {
		public Connection conn;
		public int refCount;
	}
	
	private final ThreadLocal<DatabaseConnection> connHolder = new ThreadLocal<DatabaseConnection>();
	
	private DatabaseConnection getConnection() throws SQLException {
		DatabaseConnection dbConn = connHolder.get();
		if (dbConn == null) {
			dbConn = new DatabaseConnection();
			dbConn.conn = DriverManager.getConnection("jdbc:derby:" + DATASTORE + ";create=true");
			dbConn.refCount = 0;
			connHolder.set(dbConn);
		}
		dbConn.refCount++;
		return dbConn;
	}
	
	private void releaseConnection(DatabaseConnection dbConn) throws SQLException {
		dbConn.refCount--;
		if (dbConn.refCount == 0) {
			try {
				dbConn.conn.close();
			} finally {
				connHolder.set(null);
			}
		}
	}
	
	private<E> E databaseRun(ITransaction<E> transaction) throws SQLException {
		// FIXME: retry if transaction times out due to deadlock
		
		DatabaseConnection dbConn = getConnection();
		
		try {
			boolean origAutoCommit = dbConn.conn.getAutoCommit();
			try {
				dbConn.conn.setAutoCommit(false);

				E result = transaction.run(dbConn.conn);
				dbConn.conn.commit();
				return result;
			} finally {
				dbConn.conn.setAutoCommit(origAutoCommit);
			}
		} finally {
			releaseConnection(dbConn);
		}
	}
	
	void createTables() throws SQLException {
		databaseRun(new ITransaction<Boolean>() {
			@Override
			public Boolean run(Connection conn) throws SQLException {
				
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"create table order_receipts (" +
							"  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
							"  userinfo VARCHAR(200) NOT NULL, " +
							"  price DECIMAL(10,2) " +
							")"
					);
					
					stmt.executeUpdate();
					
				} finally {
					DBUtil.closeQuietly(stmt);
				}
				
				return true;
			}
		});
	}
	
	@Override
	public OrderReceipt placeOrder(final Order order) throws SQLException {
		return databaseRun(new ITransaction<OrderReceipt>() {
			@Override
			public OrderReceipt run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					OrderReceipt receipt = new OrderReceipt();
					receipt.setUserInfo(order.getUser());
					receipt.setPrice(order.getPrice());
					
					stmt = conn.prepareStatement(
							"insert into order_receipts (userinfo, price) values (?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS
					);
					
					stmt.setString(1, receipt.getUserInfo());
					stmt.setBigDecimal(2, receipt.getPrice());
					
					stmt.executeUpdate();
					
					generatedKeys = stmt.getGeneratedKeys();
					
					if (!generatedKeys.next()) {
						throw new SQLException("Couldn't get generated key for order receipt");
					}
					
					receipt.setId(generatedKeys.getInt(1));
					
					return receipt;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(generatedKeys);
				}
			}
		});
	}
	
	@Override
	public List<OrderReceipt> getOrderReceipts() throws SQLException {
		return databaseRun(new ITransaction<List<OrderReceipt>>() {
			@Override
			public List<OrderReceipt> run(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select * from order_receipts");
					resultSet = stmt.executeQuery();
					
					List<OrderReceipt> result = new ArrayList<OrderReceipt>();
					
					while (resultSet.next()) {
						OrderReceipt receipt = new OrderReceipt();
						
						receipt.setId(resultSet.getInt(1));
						receipt.setUserInfo(resultSet.getString(2));
						receipt.setPrice(resultSet.getBigDecimal(3));
						
						result.add(receipt);
					}
					
					return result;
					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}
}
