package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.File;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class FileDelete extends HttpServlet
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
				Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
				ResultSet results = conn.createStatement().executeQuery("SELECT Login.id,File.filename FROM Login,Message,File WHERE Login.person=Message.owner AND Message.id=File.message AND File.id="+id+" AND Login.id='"+request.getRemoteUser()+"';");
				if ((results.next())||(request.isUserInRole("admin"))||(request.isUserInRole("messageadmin")))
				{
					conn.createStatement().executeUpdate("DELETE FROM File WHERE id="+id+";");
					(new File(getServletContext().getRealPath("/files/"+results.getString(2)))).delete();
					request.getRequestDispatcher(redirect).forward(request,response);
				}
				else
				{
					throw new SecurityException("User does not have the required permissions to delete that file");
				}
			}
			else
			{
				throw new IllegalArgumentException("No file id or redirect given");
			}
		}
		catch (Exception e)
		{
			throw new ServletException("Exception in FileDelete",e);
		}
	}
}