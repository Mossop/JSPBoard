package com.blueprintit.jspboard.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import com.blueprintit.jspboard.SessionHandler;

public class SessionListener implements HttpSessionListener
{
	private SessionHandler findHandler(HttpSessionEvent e)
	{
		HttpSession session = e.getSession();
		ServletContext context = session.getServletContext();
		return (SessionHandler)context.getAttribute("jspboard.SessionHandler");
	}
	
	public void sessionCreated(HttpSessionEvent e)
	{
		SessionHandler handler = findHandler(e);
		if (handler!=null)
		{
			handler.sessionCreated(e.getSession());
		}
	}
	
	public void sessionDestroyed(HttpSessionEvent e)
	{
		SessionHandler handler = findHandler(e);
		if (handler!=null)
		{
			handler.sessionDestroyed(e.getSession());
		}
	}
}
