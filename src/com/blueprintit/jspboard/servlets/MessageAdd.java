package com.blueprintit.jspboard.servlets;

import java.io.FileInputStream;
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
import com.blueprintit.jspboard.Email;
import java.util.HashMap;
import java.util.Map;

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

	private void sendEmail(Connection conn, Map updates, HttpServletRequest request)
	{
		try
		{
			ResultSet name = conn.createStatement().executeQuery("SELECT name FROM Thread WHERE id="+updates.get("thread")+";");
			name.next();
			String threadname = name.getString(1);
	
			name = conn.createStatement().executeQuery("SELECT CONCAT(title,' ',firstnames,' ',surname) AS fullname,email FROM Person,Login WHERE Person.id=Login.person AND Login.id='"+request.getRemoteUser()+"';");
			name.next();
			String person = name.getString(1);
			String email = name.getString(2);
				
			StringBuffer messageheader = new StringBuffer("The following message has been posted to the bulletin board");
			messageheader.append(" by "+person);
			messageheader.append(" in the thread \""+threadname+"\".");
			
			StringBuffer messagetext = new StringBuffer(request.getParameter("content"));
			StringBuffer messagefooter = new StringBuffer("If you wish to reply to this message on the bulletin board, please click the following link:\n\n");
			messagefooter.append("https://eeguinness.swan.ac.uk:8443"+request.getContextPath()+"/view/thread.jsp?id="+updates.get("thread")+"#unread");
	
			Map addresses = new HashMap();
			ResultSet emails = conn.createStatement().executeQuery("SELECT DISTINCT CONCAT(title,' ',firstnames,' ',surname) AS fullname,email FROM Person,Login WHERE Person.id=Login.person");
			while (emails.next())
			{
				//addresses.put(emails.getString(2),emails.getString(1));
			}
			addresses.put("dave@brass-bullet.co.uk","Dave Townsend");
			addresses.put("dtownsend@iee.org","Dave Townsend");
				
			Email.sendMailshot(messageheader.toString(),messagefooter.toString(),request.getParameter("content"),"IEE WSWYM Bulletin board message: "+threadname,email,person,addresses);
		}
		catch (Exception e)
		{
		}
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
		if ("true".equals(request.getParameter("sendemail")))
		{
			sendEmail(conn,updates,request);
		}
	}
}
