package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class MessageAdd extends TableAdd
{
	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		Convertor convert = Convertor.getConvertor("VARCHAR");
		String thread = (String)fields.get("thread");
		if (thread==null)
		{
			String folder = (String)fields.get("folder");
			String name = (String)fields.get("name");
			if ((name==null)||(folder==null))
			{
				return false;
			}
			else
			{
				try
				{
					conn.createStatement().executeUpdate("INSERT INTO Thread (folder,name,created,owner) SELECT "+folder+","+convert.convert(name)+",NOW(),person FROM Login WHERE id='"+request.getRemoteUser()+"';");
					ResultSet results = conn.createStatement().executeQuery("SELECT LAST_INSERT_ID();");
					results.next();
					thread=results.getString(1);
					fields.put("thread",thread);
					fields.remove("name");
					fields.remove("folder");
				}
				catch (Exception e)
				{
					return false;
				}
			}
		}
		if (!request.isUserInRole("messageadd"))
		{
			try
			{
				if (thread!=null)
				{
					ResultSet results = conn.createStatement().executeQuery("SELECT Login.id FROM Login,Thread WHERE Login.person=Thread.owner AND Thread.id="+thread+";");
					if (results.next())
					{
						if (!results.getString(1).equals(request.getRemoteUser()))
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
		try
		{
			ResultSet results = conn.createStatement().executeQuery("SELECT person FROM Login WHERE id='"+request.getRemoteUser()+"';");
			results.next();
			fields.put("owner",results.getString(1));
			fields.put("created","NOW()");
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	protected String getTable()
	{
		return "Message";
	}

	protected void postModification(Connection conn, Map updates, HttpServletRequest request) throws SQLException
	{
		conn.createStatement().executeUpdate("INSERT IGNORE INTO UnreadMessage (message,person) SELECT LAST_INSERT_ID(),Person.id FROM Person,Login WHERE Person.id=Login.person AND Login.id!='"+request.getRemoteUser()+"';");
	}
}
