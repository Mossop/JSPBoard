package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.File;
import com.blueprintit.jspboard.Manager;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class CategoryDelete extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			String id = request.getParameter("id");
			String redirect = request.getParameter("redirect");
			if ((id!=null)&&(redirect!=null))
			{
				if ((request.isUserInRole("contactadmin"))||(request.isUserInRole("admin")))
				{
					Connection conn = ((Manager)request.getSession().getAttribute("jspboard.Manager")).getDBConnection();
					conn.createStatement().executeUpdate("DELETE FROM Category WHERE id="+id+";");
					conn.createStatement().executeUpdate("UPDATE Person SET category=-1 WHERE category="+id+";");
					response.sendRedirect(request.getContextPath()+redirect);
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that category");
				}
			}
			else
			{
				throw new IllegalArgumentException("No category id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in CategoryDelete",e);
		}
	}
}
