package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.OutputStream;
import com.oreilly.servlet.ServletUtils;

public class FileDownload extends HttpServlet
{
	private void sendFile(HttpServletResponse response, String name, String filename, String mimetype) throws Exception
	{
		response.setContentType(mimetype);
		response.addHeader("Content-Disposition","attachment; filename=\""+name+"\"");
		OutputStream out = response.getOutputStream();
		ServletUtils.returnFile(getServletContext().getRealPath("/files/"+filename),out);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
			String id = request.getParameter("id");
			ResultSet results;
			if (id==null)
			{
				String name = request.getRequestURI();
				name = name.substring(name.lastIndexOf("/")+1);
				results = conn.createStatement().executeQuery("SELECT name,filename,mimetype FROM File WHERE name='"+name+"';");
			}
			else
			{
				results = conn.createStatement().executeQuery("SELECT name,filename,mimetype FROM File WHERE id="+id+";");
			}
			if (results.next())
			{
				sendFile(response,results.getString(1),results.getString(2),results.getString(3));
			}
			else
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND,"The file requested is not available");
			}
		}
		catch (Exception e)
		{
			log("Exception",e);
			throw new ServletException("Failed trying to download file",e);
		}
	}
}
