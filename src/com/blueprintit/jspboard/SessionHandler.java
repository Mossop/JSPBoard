package com.blueprintit.jspboard;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.DriverManager;
import java.sql.Connection;
import java.text.SimpleDateFormat;

public class SessionHandler
{
	private ServletContext context;
	private List sessions;
	private SimpleDateFormat mysqldate;
	
	public SessionHandler()
	{
		sessions = Collections.synchronizedList(new ArrayList());
		mysqldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
		}
	}
	
	public boolean isLoggedIn(String id)
	{
		Iterator loop = sessions.iterator();
		while (loop.hasNext())
		{
			HttpSession session = (HttpSession)loop.next();
			if (session.getAttribute("jspboard.user").equals(id))
			{
				return true;
			}
		}
		return false;
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
			context.log("SessionHandler: Could not establish db connection");
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
		if (session!=null)
		{
			Connection conn = (Connection)session.getAttribute("jspboard.DBConnection");
			Object user=session.getAttribute("jspboard.user");
			try
			{
				if (user!=null)
				{
					conn.createStatement().executeUpdate("UPDATE Login SET lastaccess='"+mysqldate.format(new Date(session.getLastAccessedTime()))+"' WHERE id='"+user+"';");
				}
				conn.close();
			}
			catch (Exception e)
			{
			}
			context.log("SessionHandler: Session destroyed");
		}
		else
		{
			context.log("SessionHandler: Error trying to stop null session");
		}
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
		Iterator loop = sessions.iterator();
		while (loop.hasNext())
		{
			stopSession((HttpSession)loop.next());
		}
		this.context=null;
	}
}
