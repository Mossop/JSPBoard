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

public class SessionSpy implements HttpSessionActivationListener, HttpSessionBindingListener, Serializable, Manager
{
	private String username;
	private Connection dbconn;
	private ContextManager manager;
	private HttpSession session;
		
	public SessionSpy()
	{
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
		dbconn=manager.getConnection();
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
		}
		dbconn=null;
	}
	
	public Connection getDBConnection()
	{
		return dbconn;
	}
	
	// session activation listener code
	public void sessionDidActivate(HttpSessionEvent e)
	{
		session=e.getSession();
		createConnection();
		session.getServletContext().log("SessionSpy: Database connection created");
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
		e.getSession().getServletContext().log("SessionSpy: Database connection closed");
		session=null;
	}
	
	// session binding listener code
	public void valueBound(HttpSessionBindingEvent e)
	{
		if ((e.getName().equals("jspboard.Manager"))&&(e.getValue()==this))
		{
			session=e.getSession();
			manager=(ContextManager)session.getServletContext().getAttribute("jspboard.ContextManager");
			createConnection();
			e.getSession().getServletContext().log("SessionSpy: Database connection created");
			if (manager!=null)
			{
				manager.addManager(this);
			}
		}		
	}

	public void valueUnbound(HttpSessionBindingEvent e)
	{
		if ((e.getName().equals("jspboard.Manager"))&&(e.getValue()==this))
		{
			if (manager!=null)
			{
				manager.removeManager(this);
			}
			closeConnection(e.getSession().getLastAccessedTime());
			e.getSession().getServletContext().log("SessionSpy: Database connection closed");
			session=null;
			manager=null;
		}
	}
}
