package com.blueprintit.jspboard.servlets;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;

public class RequestInfo extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException
	{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		Enumeration headers = request.getHeaders("content-type");
		while (headers.hasMoreElements())
		{
			String header = headers.nextElement().toString();
			out.println(header);
		}
	}
}
