package com.blueprintit.jspboard.servlets;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.blueprintit.jspboard.Manager;

public class FileUpload extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String path = getServletContext().getRealPath("/files");
		MultipartRequest req = new MultipartRequest(request,path,10000000,new DefaultFileRenamePolicy());
		String redirect = req.getParameter("redirect");
		String message = req.getParameter("message");
		String description = req.getParameter("description");
		Connection conn = ((Manager)session.getAttribute("jspboard.Manager")).getDBConnection();
		if (description==null)
		{
			description="";
		}
		if ((redirect!=null)&&(message!=null))
		{
			try
			{
				ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Message WHERE Login.person=Message.owner AND Message.id="+message+";");
				if ((request.isUserInRole("admin"))||(request.isUserInRole("messageadmin"))||((results.next())&&(results.getString(1).equals(request.getRemoteUser()))))
				{
					Enumeration loop = req.getFileNames();
					while (loop.hasMoreElements())
					{
						String fileid = loop.nextElement().toString();
						String name = req.getOriginalFileName(fileid);
						if (name!=null)
						{
							String filename = req.getFilesystemName(fileid);
							String content = req.getContentType(fileid);
							try
							{
								conn.createStatement().executeUpdate("INSERT INTO File (name,filename,message,description,mimetype) VALUES ('"+name+"','"+filename+"',"+message+",'"+description+"','"+content+"');");
							}
							catch (SQLException e)
							{
								req.getFile(fileid).delete();
							}
						}
					}
				}
			}
			catch (Exception e)
			{
			}
			response.sendRedirect(request.getContextPath()+redirect);
		}
		else
		{
			throw new ServletException("Invalid parameters");
		}
	}
}
