package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import com.blueprintit.jspboard.servlets.convert.Convertor;
import com.blueprintit.jspboard.RequestMultiplex;
import com.blueprintit.jspboard.Manager;
import com.blueprintit.jspboard.ContextManager;

public class ThreadUnsubscribe extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			Connection conn = ((Manager)request.getSession().getAttribute("jspboard.Manager")).getDBConnection();
			ResultSet results = conn.createStatement().executeQuery("SELECT Person.id FROM Person,Login WHERE Login.id='"+request.getRemoteUser()+"' AND Person.id=Login.person;");
			results.next();
			String query = "DELETE FROM ThreadSubscriptions WHERE thread="+request.getParameter("thread")+" AND person="+results.getString(1)+";";
			conn.createStatement().executeUpdate(query);
			String redirect = request.getParameter("redirect");
			if (redirect!=null)
			{
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+redirect));
			}
			else
			{
				String error = request.getParameter("error");
				if (error!=null)
				{
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+error));
				}
				else
				{
					throw new IllegalArgumentException("Invalid parameters passed");
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
