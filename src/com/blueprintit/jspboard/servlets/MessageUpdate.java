package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;

public class MessageUpdate extends TableUpdate
{
	protected String getTable()
	{
		return "Message";
	}

	protected void postModification(Connection conn, Map updates, String user) throws SQLException
	{
		String id = (String)updates.get("id");
		if ((user!=null)&&(id!=null))
		{
			ResultSet results = conn.createStatement().executeQuery("SELECT person FROM Login WHERE id='"+user+"';");
			if (results.next())
			{
				String person = results.getString(1);
				conn.createStatement().executeUpdate("INSERT INTO EditedMessage (message,person,altered) VALUES ("+id+","+person+",NOW());");
				conn.createStatement().executeUpdate("INSERT IGNORE INTO UnreadMessage (message,person) SELECT "+id+",Person.id FROM Person,Login WHERE Person.id=Login.person AND Person.id!="+person+";");
			}
		}
	}
}
