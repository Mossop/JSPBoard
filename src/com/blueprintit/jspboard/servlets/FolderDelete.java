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

public class FolderDelete extends HttpServlet
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
				if ((request.isUserInRole("boardadmin"))||(request.isUserInRole("admin")))
				{
					Connection conn = ((Manager)request.getSession().getAttribute("jspboard.Manager")).getDBConnection();
					ResultSet results = conn.createStatement().executeQuery("SELECT id FROM Thread WHERE folder="+id+";");
					if (results.next())
					{
						throw new IllegalArgumentException("You can only delete empty folders at the moment.");
					}
					else
					{
						conn.createStatement().executeUpdate("DELETE FROM Folder WHERE id="+id+";");
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+redirect));
					}
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that folder");
				}
			}
			else
			{
				throw new IllegalArgumentException("No folder id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in FolderDelete",e);
		}
	}
}
