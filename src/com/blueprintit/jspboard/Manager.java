package com.blueprintit.jspboard;

import java.sql.Connection;

public interface Manager
{
	public void setUsername(String name);
	
	public String getUsername();
	
	public Connection getDBConnection();
}
