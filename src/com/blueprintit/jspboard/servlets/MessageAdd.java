package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Map;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import com.blueprintit.jspboard.servlets.convert.Convertor;
import com.blueprintit.jspboard.RequestMultiplex;

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
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO Thread (folder,name,created,owner) SELECT "+folder+","+convert.convert(name)+",NOW(),person FROM Login WHERE id='"+request.getRemoteUser()+"';");
					ResultSet ids = stmt.getGeneratedKeys();
					ids.next();
					thread=ids.getString(1);
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
		if ((!request.isUserInRole("messageadd"))&&(!request.isUserInRole("admin")))
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

	protected void postModification(Connection conn, Map updates, HttpServletRequest request, ResultSet keys) throws SQLException
	{
		keys.next();
		String id=keys.getString(1);
		conn.createStatement().executeUpdate("INSERT IGNORE INTO UnreadMessage (message,person) SELECT "+id+",Person.id FROM Person,Login WHERE Person.id=Login.person AND Login.id!='"+request.getRemoteUser()+"' GROUP BY Person.id;");
		String description = request.getParameter("description");
		Enumeration loop = ((RequestMultiplex)request).getFileNames();
		while (loop.hasMoreElements())
		{
			String fileid = loop.nextElement().toString();
			String name = ((RequestMultiplex)request).getOriginalFileName(fileid);
			if (name!=null)
			{
				String filename = ((RequestMultiplex)request).getFilesystemName(fileid);
				String content = ((RequestMultiplex)request).getContentType(fileid);
				try
				{
					conn.createStatement().executeUpdate("INSERT INTO File (name,filename,message,description,mimetype) VALUES ('"+name+"','"+filename+"',"+id+",'"+description+"','"+content+"');");
				}
				catch (SQLException e)
				{
					((RequestMultiplex)request).getFile(fileid).delete();
				}
			}
		}
	}
}
