package com.blueprintit.jspboard.servlets;

import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class FolderUpdate extends TableUpdate
{
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		return ((request.isUserInRole("admin"))||(request.isUserInRole("boardadmin")));
	}
	
	protected String getTable()
	{
		return "Folder";
	}
}
