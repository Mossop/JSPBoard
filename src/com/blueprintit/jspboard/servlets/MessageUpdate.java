package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public class MessageUpdate extends TableUpdate
{
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
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
					ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Message WHERE Login.person=Message.owner AND Message.id="+id+";");
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
		return "Message";
	}

	protected void postModification(Connection conn, Map updates, HttpServletRequest request) throws SQLException
	{
		String id = (String)updates.get("id");
		if (id!=null)
		{
			ResultSet results = conn.createStatement().executeQuery("SELECT person FROM Login WHERE id='"+request.getRemoteUser()+"';");
			if (results.next())
			{
				String person = results.getString(1);
				conn.createStatement().executeUpdate("INSERT INTO EditedMessage (message,person,altered) VALUES ("+id+","+person+",NOW());");
				conn.createStatement().executeUpdate("INSERT IGNORE INTO UnreadMessage (message,person) SELECT "+id+",Person.id FROM Person,Login WHERE Person.id=Login.person AND Person.id!="+person+" GROUP BY Person.id;");
			}
		}
	}
}
