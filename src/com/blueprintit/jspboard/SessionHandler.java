package com.blueprintit.jspboard;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.DriverManager;
import java.sql.Connection;

public class SessionHandler
{
	private ServletContext context;
	private List sessions;
	
	public SessionHandler()
	{
		sessions = Collections.synchronizedList(new ArrayList());
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
		}
	}
	
	public List getSessions()
	{
		return Collections.unmodifiableList(sessions);
	}
	
	private Connection newConnection()
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://localhost/JSPBoard","JSPBoard","jspb379");
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	private void startSession(HttpSession session)
	{
		context.log("SessionHandler: Session created");
		session.setAttribute("jspboard.DBConnection",newConnection());
	}
	
	private void stopSession(HttpSession session)
	{
		Connection conn = (Connection)session.getAttribute("jspboard.DBConnection");
		Object user=session.getAttribute("jspboard.user");
		try
		{
			if (user!=null)
			{
				conn.createStatement().executeUpdate("UPDATE Login SET lastaccess=NOW() WHERE id='"+user+"';");
			}
			conn.close();
		}
		catch (Exception e)
		{
		}
		context.log("SessionHandler: Session destroyed");
	}
	
	public void sessionCreated(HttpSession session)
	{
		startSession(session);
		sessions.add(session);
	}
	
	public void sessionDestroyed(HttpSession session)
	{
		sessions.remove(session);
		stopSession(session);
	}

	public void contextInitialised(ServletContext context)
	{
		this.context=context;
		context.log("SessionHandler: Context started");
	}
	
	public void contextDestroyed(ServletContext context)
	{
		context.log("SessionHandler: Context destroyed");
		this.context=null;
		Iterator loop = sessions.iterator();
		while (loop.hasNext())
		{
			stopSession((HttpSession)loop.next());
		}
	}
}
