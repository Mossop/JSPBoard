package com.blueprintit.jspboard.servlets;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException
	{
		HttpSession session = request.getSession(false);
		if (session!=null)
		{
			session.invalidate();
		}
		request.getRequestDispatcher("/logout.jsp").forward(request,response);
	}
}
