package com.blueprintit.jspboard.listeners;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import com.blueprintit.jspboard.SessionHandler;

public class ContextListener implements ServletContextListener
{
	private SessionHandler handler = new SessionHandler();
	
	public void contextInitialized(ServletContextEvent e)
	{
		handler.contextInitialised(e.getServletContext());
		e.getServletContext().setAttribute("jspboard.SessionHandler",handler);
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		handler.contextDestroyed(e.getServletContext());
		e.getServletContext().removeAttribute("jspboard.SessionHandler");
	}
}
