package com.blueprintit.jspboard;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
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
	
	public SessionSpy()
	{
	}
	
	public void setUsername(String name)
	{
		username=name;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	private void createConnection()
	{
		dbconn=ContextManager.getConnection();
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
		createConnection();
		e.getSession().getServletContext().log("SessionSpy: Database connection created");
	}
	
	public void sessionWillPassivate(HttpSessionEvent e)
	{
		closeConnection(e.getSession().getLastAccessedTime());
		e.getSession().getServletContext().log("SessionSpy: Database connection closed");
	}
	
	// session binding listener code
	public void valueBound(HttpSessionBindingEvent e)
	{
		if ((e.getName().equals("jspboard.Manager"))&&(e.getValue()==this))
		{
			createConnection();
			e.getSession().getServletContext().log("SessionSpy: Database connection created");
		}		
	}

	public void valueUnbound(HttpSessionBindingEvent e)
	{
		if ((e.getName().equals("jspboard.Manager"))&&(e.getValue()==this))
		{
			closeConnection(e.getSession().getLastAccessedTime());
			e.getSession().getServletContext().log("SessionSpy: Database connection closed");
		}
	}
}
