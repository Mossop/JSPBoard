package com.blueprintit.jspboard.servlets;

import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class LoginUpdate extends TableUpdate
{
	protected void prepareFields(Connection conn) throws Exception
	{
		super.prepareFields(conn);
		fields.put("password",Convertor.getConvertor("MD5PASS"));
	}
	
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		fields.remove("lastaccess");
		fields.remove("person");
		return ((request.isUserInRole("admin"))||(request.isUserInRole("loginadmin")));
	}
	
	protected String getTable()
	{
		return "Login";
	}
}
