package com.blueprintit.jspboard;

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
import java.util.Map;
import java.util.Iterator;
import com.blueprintit.jspboard.Stylise;
import javax.naming.NamingException;
import java.io.UnsupportedEncodingException;

public class Email
{
	public static void sendMailshot(String message, String subject, String fromemail, String fromname, Map to)
	{
		sendMailshot(null,null,message,subject,fromemail,fromname,to);
	}

	public static void sendEmail(String message, String subject, String fromemail, String fromname, Map to, Map cc, Map bcc)
	{
		sendEmail(null,null,message,subject,fromemail,fromname,to,cc,bcc);
	}

	private static Message prepareEmail(String header, String footer, String messagetext, String subject, String fromemail, String fromname) throws MessagingException, NamingException, UnsupportedEncodingException
	{
		StringBuffer messageplain = new StringBuffer(messagetext);
		StringBuffer messagehtml = new StringBuffer(Stylise.styliseEmail(messagetext));
		
		if (header!=null)
		{
			messageplain.insert(0,"\n---------------------------------------------------------------------------\n\n");
			messageplain.insert(0,header);
		}
		if (footer!=null)
		{
			messageplain.append("\n---------------------------------------------------------------------------\n");
			messageplain.append(footer);
		}
		MimeBodyPart plain = new MimeBodyPart();
		plain.setContent(messageplain.toString(),"text/plain");
		
		if (header!=null)
		{
			messagehtml.insert(0,"</p>\n<hr>\n<p>");
			messagehtml.insert(0,Stylise.styliseEmail(header));
		}
		messagehtml.insert(0,"<html>\n<body>\n<p>");
		if (footer!=null)
		{
			messagehtml.append("</p>\n<hr>\n<p>");
			messagehtml.append(Stylise.styliseEmail(footer));
		}
		messagehtml.append("</p>\n</body>\n</html>\n");
		MimeBodyPart html = new MimeBodyPart();
		html.setContent(messagehtml.toString(),"text/html");
		
		MimeMultipart full = new MimeMultipart("alternate");
		full.addBodyPart(plain);
		full.addBodyPart(html);

		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		Session msession = (Session) envCtx.lookup("mail/Session");

		Message message = new MimeMessage(msession);
		message.setFrom(new InternetAddress(fromemail,fromname));
		message.setSubject(subject);
		message.setContent(full);
		
		return message;
	}
	
	public static void sendEmail(String header, String footer, String content, String subject, String fromemail, String fromname, Map to, Map cc, Map bcc)
	{
		try
		{
			Message message = prepareEmail(header,footer,content,subject,fromemail,fromname);
			Iterator loop;
			if (to!=null)
			{
				loop = to.keySet().iterator();
				while (loop.hasNext())
				{
					String email = loop.next().toString();
					String name = to.get(email).toString();
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email,name));
				}
			}
			if (cc!=null)
			{
				loop = cc.keySet().iterator();
				while (loop.hasNext())
				{
					String email = loop.next().toString();
					String name = cc.get(email).toString();
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(email,name));
				}
			}
			if (bcc!=null)
			{
				loop = bcc.keySet().iterator();
				while (loop.hasNext())
				{
					String email = loop.next().toString();
					String name = bcc.get(email).toString();
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(email,name));
				}
			}
			Transport.send(message);
		}
		catch (Exception e)
		{
		}
	}

	public static void sendMailshot(String header, String footer, String content, String subject, String fromemail, String fromname, Map to)
	{
		try
		{
			Message message = prepareEmail(header,footer,content,subject,fromemail,fromname);
			Iterator loop;
			if (to!=null)
			{
				loop = to.keySet().iterator();
				while (loop.hasNext())
				{
					String email = loop.next().toString();
					String name = to.get(email).toString();
					message.setRecipient(Message.RecipientType.TO, new InternetAddress(email,name));
					Transport.send(message);
				}
			}
		}
		catch (Exception e)
		{
		}
	}
}
