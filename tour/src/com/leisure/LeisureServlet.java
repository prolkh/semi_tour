package com.leisure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Util.MyServlet;

@WebServlet("/leisure/*")
public class LeisureServlet extends MyServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
