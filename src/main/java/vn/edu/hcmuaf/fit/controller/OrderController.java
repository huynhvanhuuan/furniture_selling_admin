package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CheckoutService;
import vn.edu.hcmuaf.fit.service.impl.CheckoutServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderController", value = "/admin/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CheckoutService checkoutService;
	
	@Override
	public void init() throws ServletException {
		checkoutService = new CheckoutServiceImpl();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
