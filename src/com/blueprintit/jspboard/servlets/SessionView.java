package com.blueprintit.jspboard.servlets;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.blueprintit.jspboard.SessionHandler;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.text.SimpleDateFormat;

public class SessionView extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			out.println("<html>\n<head>\n<title>Session Viewer</title>\n</head><body>");
			try
			{
				SessionHandler handler = (SessionHandler)getServletContext().getAttribute("jspboard.SessionHandler");
				List sessions = handler.getSessions();
				SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
				out.println("<table>\n<tr>\n");
				out.println("<td><b>Id</b></td>");
				out.println("<td><b>Created</b></td>");
				out.println("<td><b>Last access</b></td>");
				out.println("<td><b>User</b></td>");
				out.println("</tr>");
				out.println("<tr><td colspan=4><hr></td></tr>");
				Iterator loop = sessions.iterator();
				while (loop.hasNext())
				{
					HttpSession session = (HttpSession)loop.next();
					out.println("<tr>");
					out.println("<td>"+session.getId()+"</td>");
					out.println("<td>"+df.format(new Date(session.getCreationTime()))+"</td>");
					out.println("<td>"+df.format(new Date(session.getLastAccessedTime()))+"</td>");
					out.println("<td>"+session.getAttribute("jspboard.user")+"</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
			catch (Exception e)
			{
				out.println("Error, could not connect to session handler");
			}
			out.println("</body>");
			out.flush();
		}
		catch (IOException e)
		{
			log("error getting writer",e);
		}
	}
}
