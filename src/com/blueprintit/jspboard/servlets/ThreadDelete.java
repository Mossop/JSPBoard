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

public class ThreadDelete extends HttpServlet
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
				ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Thread WHERE Login.person=Thread.owner AND Login.id='"+request.getRemoteUser()+"';");
				if ((request.isUserInRole("admin"))||(request.isUserInRole("messageadmin"))||(results.next()))
				{
					ResultSet msgs = conn.createStatement().executeQuery("SELECT Message.id FROM Message WHERE Message.thread="+id+";");
					while (msgs.next())
					{
						conn.createStatement().executeUpdate("DELETE FROM EditedMessage WHERE message="+msgs.getString(1)+";");
						conn.createStatement().executeUpdate("DELETE FROM UnreadMessage WHERE message="+msgs.getString(1)+";");
					}
					ResultSet files = conn.createStatement().executeQuery("SELECT File.id,filename FROM Message,File WHERE File.message=Message.id AND Message.thread="+id+";");
					while (files.next())
					{
						conn.createStatement().executeUpdate("DELETE FROM File WHERE id="+files.getString(1)+";");
						(new File(getServletContext().getRealPath("/files/"+files.getString(2)))).delete();
					}
					conn.createStatement().executeUpdate("DELETE FROM Message WHERE thread="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM ThreadSubscriptions WHERE thread="+id+";");
					conn.createStatement().executeUpdate("DELETE FROM Thread WHERE id="+id+";");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+redirect));
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that thread");
				}
			}
			else
			{
				throw new IllegalArgumentException("No thread id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in ThreadDelete",e);
		}
	}
}
