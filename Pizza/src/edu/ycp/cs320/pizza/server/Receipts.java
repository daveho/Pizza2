package edu.ycp.cs320.pizza.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web service for accessing order receipts.
 */
public class Receipts extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// The path info is the part of the URL that follows the
		// part identifying the servlet.  For example, if the
		// URL was http://hostname/receipts/42, and assuming that
		// "/receipts" is the path to the servlet, the path info
		// would be "/42".
		String info = req.getPathInfo();
		System.out.println("Path info is: " + info);
		
		// TODO: handle the request
		
		// TODO: write the response body with data from the database
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/plain");
		resp.getWriter().println("Success!");
	}
}
