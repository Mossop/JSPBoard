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
	private List managers;
		
	public ContextManager()
	{
		managers = Collections.synchronizedList(new ArrayList());
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
		}
	}
	
	public boolean isLoggedIn(String user)
	{
		boolean found=false;
		Iterator loop = managers.iterator();
		while ((loop.hasNext())&&(!found))
		{
			Manager manager = (Manager)loop.next();
			if (manager!=null)
			{
				found=manager.getUsername().equals(user);
			}
		}
		return found;
	}
	
	public void addManager(Manager session)
	{
		managers.add(session);
	}
	
	public void removeManager(Manager session)
	{
		managers.remove(session);
	}
	
	public List getManagers()
	{
		return Collections.unmodifiableList(managers);
	}
	
	public Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://localhost/JSPBoard","JSPBoard","jspb379");
		}
		catch (Exception e)
		{
			context.log("Error establishing database connection",e);
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
			managers.clear();
		}
	}

	// session listener code
	public void sessionCreated(HttpSessionEvent e)
	{
		//context.log("ContextManager: Session created");
		e.getSession().setAttribute("jspboard.Manager",new SessionSpy());
	}
	
	public void sessionDestroyed(HttpSessionEvent e)
	{
		//context.log("ContextManager: Session destroyed");
	}
}
