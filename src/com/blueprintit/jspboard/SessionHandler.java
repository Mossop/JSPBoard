package com.blueprintit.jspboard;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
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

public class SessionHandler implements HttpSessionListener, ServletContextListener, HttpSessionAttributeListener
{
	private ServletContext context;
	private List sessions;
	private SimpleDateFormat mysqldate;
	private Map users;
		
	public SessionHandler()
	{
		users = new HashMap();
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
			context.log("SessionHandler: Could not establish DB connection",e);
			return null;
		}
	}
	
	// context listener code
	public void contextInitialized(ServletContextEvent e)
	{
		context=e.getServletContext();
		e.getServletContext().setAttribute("jspboard.SessionHandler",this);
		context.log("SessionHandler: Context started");
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		e.getServletContext().removeAttribute("jspboard.SessionHandler");
		context.log("SessionHandler: Context destroyed");
		this.context=null;
	}

	// session listener code
	public void sessionCreated(HttpSessionEvent e)
	{
		context.log("SessionHandler: Session created");
		e.getSession().setAttribute("jspboard.DBConnection",newConnection());
		sessions.add(e.getSession());
	}
	
	public void sessionDestroyed(HttpSessionEvent e)
	{
		context.log("SessionHandler: Session destroyed");
		sessions.remove(e.getSession());
	}
	
	// session attribute listener code
	public void attributeAdded(HttpSessionBindingEvent se)
	{
		if (se.getName().equals("jspboard.user"))
		{
			users.put(se.getSession(),se.getValue());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se)
	{
		if (se.getName().equals("jspboard.DBConnection"))
		{
			try
			{
				Connection conn = (Connection)se.getValue();
				String user = (String)users.get(se.getSession());
				if (conn!=null)
				{
					if (user!=null)
					{
						conn.createStatement().executeUpdate("UPDATE Login SET lastaccess='"+mysqldate.format(new Date(se.getSession().getLastAccessedTime()))+"' WHERE id='"+user+"';");
					}
					conn.close();
				}
			}
			catch (Exception e)
			{
				context.log("SessionHandler: Exception closing DB connection",e);
			}
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent se)
	{
		attributeAdded(se);
	}
}
