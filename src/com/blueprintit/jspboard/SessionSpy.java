package com.blueprintit.jspboard;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Date;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;

public class SessionSpy implements HttpSessionActivationListener, Serializable, Manager
{
	private String username;
	private Connection dbconn;
	private ContextManager manager;
	private HttpSession session;
		
	public SessionSpy(HttpSession session)
	{
		this.session=session;
	}
	
	public void setUsername(String name)
	{
		if (manager==null)
		{
			manager = (ContextManager)session.getServletContext().getAttribute("jspboard.ContextManager");
			if (manager!=null)
			{
				manager.addManager(this);
			}
		}
		username=name;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public HttpSession getSession()
	{
		return session;
	}
	
	private void createConnection()
	{
		if (manager==null)
		{
			manager = (ContextManager)session.getServletContext().getAttribute("jspboard.ContextManager");
			if (manager!=null)
			{
				manager.addManager(this);
			}
		}
		if (manager!=null)
		{
			dbconn=manager.getConnection();
			session.getServletContext().log("SessionSpy: Database connection created");
		}
	}
	
	private void closeConnection(long time)
	{
		if ((username!=null)&&(dbconn!=null))
		{
			try
			{
				SimpleDateFormat mysqldate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dbconn.createStatement().executeUpdate("UPDATE Login SET lastaccess='"+mysqldate.format(new Date(time))+"' WHERE id='"+username+"';");
				dbconn.close();
			}
			catch (Exception e)
			{
			}
			session.getServletContext().log("SessionSpy: Database connection closed");
		}
		dbconn=null;
	}
	
	public Connection getDBConnection()
	{
		if (dbconn==null)
		{
			createConnection();
		}
		return dbconn;
	}
	
	// session activation listener code
	public void sessionDidActivate(HttpSessionEvent e)
	{
		session=e.getSession();
		createConnection();
		manager = (ContextManager)session.getServletContext().getAttribute("jspboard.ContextManager");
		if (manager!=null)
		{
			manager.addManager(this);
		}
	}
	
	public void sessionWillPassivate(HttpSessionEvent e)
	{
		if (manager!=null)
		{
			manager.removeManager(this);
			manager=null;
		}
		closeConnection(e.getSession().getLastAccessedTime());
		session=null;
	}
}
