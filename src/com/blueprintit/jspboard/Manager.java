package com.blueprintit.jspboard;

import java.sql.Connection;
import javax.servlet.http.HttpSession;

public interface Manager
{
	public HttpSession getSession();
	
	public void setUsername(String name);
	
	public String getUsername();
	
	public Connection getDBConnection();
}
