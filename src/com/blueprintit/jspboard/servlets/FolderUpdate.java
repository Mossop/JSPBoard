package com.blueprintit.jspboard.servlets;

import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class FolderUpdate extends TableUpdate
{
	protected boolean allowUpdate(Connection conn, Map fields, HttpServletRequest request)
	{
		return (request.isUserInRole("boardadmin"));
	}
	
	protected String getTable()
	{
		return "Folder";
	}
}
