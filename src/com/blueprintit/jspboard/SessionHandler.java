package com.blueprintit.jspboard;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.DriverManager;
import java.sql.Connection;
import java.text.SimpleDateFormat;

public class SessionHandler implements HttpSessionListener, ServletContextListener
{
	private ServletContext context;
	private List sessions;
	private Map dbconns;
	private SimpleDateFormat mysqldate;
	
	public SessionHandler()
	{
		sessions = Collections.synchronizedList(new ArrayList());
		dbconns = new HashMap();
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
		Object user;
		while (loop.hasNext())
		{
			HttpSession session = (HttpSession)loop.next();
			user=session.getAttribute("jspboard.user");
			if ((user!=null)&&(user.equals(id)))
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
		Connection conn = newConnection();
		dbconns.put(session,conn);
		session.setAttribute("jspboard.DBConnection",conn);
	}
	
	private void stopSession(HttpSession session)
	{
		if (session!=null)
		{
			Connection conn = (Connection)dbconns.get(session);
			Object user=session.getAttribute("jspboard.user");
			try
			{
				if ((user!=null)&&(conn!=null))
				{
					conn.createStatement().executeUpdate("UPDATE Login SET lastaccess='"+mysqldate.format(new Date(session.getLastAccessedTime()))+"' WHERE id='"+user+"';");
				}
				conn.close();
			}
			catch (Exception e)
			{
			}
			dbconns.remove(session);
			context.log("SessionHandler: Session destroyed");
		}
		else
		{
			context.log("SessionHandler: Error trying to stop null session");
		}
	}
	
	// context listener code
	public void contextInitialized(ServletContextEvent e)
	{
		e.getServletContext().setAttribute("jspboard.SessionHandler",this);
		context.log("SessionHandler: Context started");
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		e.getServletContext().removeAttribute("jspboard.SessionHandler");
		Iterator loop = sessions.iterator();
		while (loop.hasNext())
		{
			stopSession((HttpSession)loop.next());
		}
		this.context=null;
		context.log("SessionHandler: Context destroyed");
	}

	// session listener code
	public void sessionCreated(HttpSessionEvent e)
	{
		sessions.add(e.getSession());
		startSession(e.getSession());
	}
	
	public void sessionDestroyed(HttpSessionEvent e)
	{
		stopSession(e.getSession());
		sessions.remove(e.getSession());
	}
}
