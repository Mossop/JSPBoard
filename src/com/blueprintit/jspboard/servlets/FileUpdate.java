package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public class FileUpdate extends TableUpdate
{
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		fields.remove("filename");
		if ((request.isUserInRole("admin"))||(request.isUserInRole("messageadmin")))
		{
			return true;
		}
		else
		{
			try
			{
				String id = (String)fields.get("id");
				if (id!=null)
				{
					ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Message,File WHERE Login.person=Message.owner AND Message.id=File.message AND File.id="+id+";");
					if (results.next())
					{
						if (results.getString(1).equals(request.getRemoteUser()))
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			catch (Exception e)
			{
				return false;
			}
		}
	}
	
	protected String getTable()
	{
		return "File";
	}
}
