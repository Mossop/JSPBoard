package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.File;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class PersonDelete extends HttpServlet
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
					Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
					conn.createStatement().executeUpdate("DELETE FROM Person WHERE id="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM UnreadMessage WHERE person="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM Login WHERE person="+id+";");
					conn.createStatement().executeUpdate("UPDATE EditedMessage SET person=NULL WHERE person="+id+";");
					conn.createStatement().executeUpdate("UPDATE Message SET owner=NULL WHERE owner="+id+";");
					conn.createStatement().executeUpdate("UPDATE Thread SET owner=NULL WHERE owner="+id+";");
					response.sendRedirect(request.getContextPath()+redirect);
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that person");
				}
			}
			else
			{
				throw new IllegalArgumentException("No person id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in PersonDelete",e);
		}
	}
}
