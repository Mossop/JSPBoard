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

public class MessageDelete extends HttpServlet
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
				Connection conn = ((Manager)request.getSession().getAttribute("jspboard.Manager")).getDBConnection();
				ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Message WHERE Login.person=Message.owner AND Login.id='"+request.getRemoteUser()+"';");
				if ((request.isUserInRole("admin"))||(request.isUserInRole("messageadmin"))||(results.next()))
				{
					ResultSet files = conn.createStatement().executeQuery("SELECT filename FROM File WHERE message="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM File WHERE message="+id+";");
					while (files.next())
					{
						(new File(getServletContext().getInitParameter("jspboard.Repository")+"/"+files.getString(1))).delete();
					}
					conn.createStatement().executeUpdate("DELETE FROM UnreadMessage WHERE message="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM EditedMessage WHERE message="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM Message WHERE id="+id+";");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+redirect));
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that message");
				}
			}
			else
			{
				throw new IllegalArgumentException("No message id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in MessageDelete",e);
		}
	}
}
