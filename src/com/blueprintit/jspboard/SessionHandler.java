package com.blueprintit.jspboard;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SessionHandler
{
	private ServletContext context;
	private List sessions;
	
	public SessionHandler()
	{
		sessions = Collections.synchronizedList(new ArrayList());
	}
	
	public List getSessions()
	{
		return Collections.unmodifiableList(sessions);
	}
	
	public void sessionCreated(HttpSession session)
	{
		context.log("SessionHandler: Session created");
		sessions.add(session);
	}
	
	public void sessionDestroyed(HttpSession session)
	{
		context.log("SessionHandler: Session destroyed");
		sessions.remove(session);
	}

	public void contextInitialised(ServletContext context)
	{
		this.context=context;
		context.log("SessionHandler: Context started");
	}
	
	public void contextDestroyed(ServletContext context)
	{
		context.log("SessionHandler: Context destroyed");
		this.context=null;
	}
}
