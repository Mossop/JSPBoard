package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class PersonAdd extends TableAdd
{
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		if ((request.isUserInRole("admin"))||(request.isUserInRole("contactedit")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected String getTable()
	{
		return "Person";
	}
}
