package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class LoginAdd extends TableAdd
{
	protected void prepareFields(Connection conn) throws Exception
	{
		super.prepareFields(conn);
		fields.put("password",Convertor.getConvertor("MD5PASS"));
	}
	
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		if ((request.isUserInRole("admin"))||(request.isUserInRole("loginadmin")))
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
		return "Login";
	}
}
