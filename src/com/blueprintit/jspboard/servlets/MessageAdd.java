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
import com.blueprintit.jspboard.Stylise;
import com.blueprintit.jspboard.RequestMultiplex;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.MessagingException;

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
			
			StringBuffer messagetext = new StringBuffer("The following message has been posted to the bulletin board");
			messagetext.append(" by "+person);
			messagetext.append(" in the thread \""+threadname+"\".\n\n");
			messagetext.append(request.getParameter("content"));
			messagetext.append("\n\nIf you wish to reply to this message on the bulletin board, please click the following link:\n\n");
			messagetext.append("https://eeguinness.swan.ac.uk:8443"+request.getContextPath()+"/view/thread.jsp?id="+updates.get("thread")+"#unread\n");

			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Session msession = (Session) envCtx.lookup("mail/Session");

			Message message = new MimeMessage(msession);
			ResultSet emails = conn.createStatement().executeQuery("SELECT DISTINCT CONCAT(title,' ',firstnames,' ',surname) AS fullname,email FROM Person,Login WHERE Person.id=Login.person");
			while (emails.next())
			{
				//message.addRecipient(Message.RecipientType.BCC,new InternetAddress(emails.getString(2),emails.getString(1)));
			}
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("dave@brass-bullet.co.uk","Dave Townsend"));
			
			message.setFrom(new InternetAddress(email,person));
			message.setSubject("IEE WSWYM Bulletin board message: "+threadname);
			MimeMultipart text = new MimeMultipart("alternate");
			MimeBodyPart plain = new MimeBodyPart();
			plain.setContent(messagetext.toString(),"text/plain");
			MimeBodyPart html = new MimeBodyPart();
			html.setContent("<html>\n<body>\n<p>"+Stylise.styliseEmail(messagetext.toString())+"</p></body></html>","text/html");
			text.addBodyPart(plain);
			text.addBodyPart(html);
			
			/*Enumeration loop = ((RequestMultiplex)request).getFileNames();
			if (loop.hasMoreElements())
			{
				MimeMultipart full = new MimeMultipart();
				MimeBodyPart textpart = new MimeBodyPart();
				textpart.setContent(text);
				full.addBodyPart(textpart);
				
				while (loop.hasMoreElements())
				{
					String fileid = loop.nextElement().toString();
					String name = ((RequestMultiplex)request).getOriginalFileName(fileid);
					if (name!=null)
					{
						String content = ((RequestMultiplex)request).getContentType(fileid);
						MimeBodyPart filepart = new MimeBodyPart(new FileInputStream(((RequestMultiplex)request).getFilesystemName(fileid)));
						filepart.setContentType(content);
						full.addBodyPart(filepart);
					}
				}
				text=full;
			}*/

			message.setContent(text);
			Transport.send(message);
		}
		catch (Exception e)
		{
			log("Exception sending mail",e);
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
		sendEmail(conn,updates,request);
	}
}
