package com.blueprintit.jspboard;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.naming.InitialContext;
import javax.naming.Context;
import java.sql.DriverManager;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;

public class ContextManager implements HttpSessionListener, ServletContextListener
{
	private ServletContext context;
	private List sessions;
	private Map users;
		
	public ContextManager()
	{
		users = Collections.synchronizedMap(new HashMap());
		sessions = Collections.synchronizedList(new ArrayList());
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
		return (users.containsKey(id));
	}
	
	public List getSessions()
	{
		return Collections.unmodifiableList(sessions);
	}
	
	public static Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://localhost/JSPBoard","JSPBoard","jspb379");
		}
		catch (Exception e)
		{
			//context.log("Error establishing database connection",e);
			return null;
		}
	}
	
	// context listener code
	public void contextInitialized(ServletContextEvent e)
	{
		context=e.getServletContext();
		context.setAttribute("jspboard.ContextManager",this);
		context.log("ContextManager: Context started");
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		if (context==e.getServletContext())
		{
			context.removeAttribute("jspboard.Contextmanager");
			context.log("ContextManager: Context destroyed");
			this.context=null;
		}
	}

	// session listener code
	public void sessionCreated(HttpSessionEvent e)
	{
		//context.log("ContextManager: Session created");
		e.getSession().setAttribute("jspboard.Manager",new SessionSpy());
		sessions.add(e.getSession());
	}
	
	public void sessionDestroyed(HttpSessionEvent e)
	{
		//context.log("ContextManager: Session destroyed");
		sessions.remove(e.getSession());
	}
}
