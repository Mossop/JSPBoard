package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class Password extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			String pw1 = request.getParameter("password1");
			String pw2 = request.getParameter("password2");
			String redirect = request.getParameter("redirect");
			if (redirect==null)
			{
				throw new IllegalArgumentException("No redirect given");
			}
			if ((pw1!=null)&&(pw2!=null)&&(pw1.equals(pw2)))
			{
				Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
				Convertor pass = Convertor.getConvertor("MD5PASS");
				conn.createStatement().executeUpdate("UPDATE Login SET password="+pass.convert(pw1)+" WHERE id='"+request.getRemoteUser()+"';");
				request.getRequestDispatcher(redirect).forward(request,response);
			}
			else
			{
				String error = request.getParameter("error");
				if (error!=null)
				{
					request.getRequestDispatcher(error).forward(request,response);
				}
				else
				{
					throw new IllegalArgumentException("Passwords did not match and no error page given");
				}
			}
		}
		catch (Exception e)
		{
			log("Exception",e);
			throw new ServletException("Failed trying to update database",e);
		}
	}
}
